/**
 * 
 */
package com.example.tutorial.pages.add;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.PostKind;
import com.example.tutorial.entities.Subcategory;
import com.example.tutorial.pages.Index;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.ImageUtil;

public class CreateAdd {
	@Inject
	private SettingsDAO settingsDAO;
	
	@Property
	private boolean submited;
	
	@Inject
	private BaseURLSource bus;
	
	@Inject
	private ApplicationGlobals appGlob;
	
	@Inject
	private RequestGlobals requestGlob;
	
	//@InjectObject("service-property:tapestry.globals.HttpServletRequest:remoteAddr")
	//public abstract String getRemoteAddr()
	
	@Inject
	private HttpServletRequest m;
	
	@Property
	private Subcategory subcategory;

	@Inject
	private SubcategoryDAO subcategoryDAO;

	@Component
	private Form postForm;
	
	@Property
	@Validate("required,maxlength=85")
	private String title;
	
	@Property
	private String videoLink;
	
	@Property
	private String selectedKind;
	
	@Property
	private String type;
	
	@Property
	@Validate("required,maxlength=120000")
	private String content;
	
	@Property
	@Validate("required,maxlength=5")
	private String price;

	@Property
	private String area;

	@Property
	private String postal;

	@Property
	@Validate("required,maxlength=25")
	private String phone;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private Authenticator authenticator;
	
	@Inject
	private PostDAO postDAO;
	
	@Property
	private String locationName;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private com.example.tutorial.pages.view.Subcategory subcatPage;
	
	public boolean getShowLocation() {
		return settingsDAO.getSettings().showLocationOnPostAds;
	}
	
	public boolean getOfferingWanted() {
		return settingsDAO.getSettings().offeringWanted;
	}
	
	Object onActivate(Long id) {
		this.subcategory = subcategoryDAO.getByID(id);
		
		//if (!postDAO.getUser(id).equals(authenticator.getLoggedUser().id))
			//return Index.class;
		
		if (lastPostsID == null) {
			lastPostsID = id;
		} else if (!lastPostsID.equals(id)) {
			uploadedFiles = new ArrayList<UploadedFile>();
			lastPostsID = id;
		}
		
		if (authenticator.getLoggedUser().phone!=null)
			phone = authenticator.getLoggedUser().phone;
		
		return null;
	}
	
	long onPassivate() {
		return this.subcategory.id;
	}
	
	public List<String> getValidKinds() {
		ArrayList<String> list = new ArrayList<String>(2);
		list.add(PostKind.OFERING.toString());
		list.add(PostKind.WANTED.toString());
		return list;
	}
	
	@Property
    private String kaptcha;
	
	public boolean getVideoOnPost() {
		return settingsDAO.getSettings().allowVideoOnPost;
	}
	
	
	@OnEvent(value = EventConstants.SUCCESS, component = "PostForm")
    public Object createAdd() {
		if (!submited) {
			//if (1==1) {
				//throw new RuntimeException(title);
			//}
			
			Post post = new Post();
			
			if (selectedKind!=null && settingsDAO.getSettings().offeringWanted)
				post.kind = selectedKind.equals(PostKind.OFERING.toString())?PostKind.OFERING:PostKind.WANTED;
			
			post.title = title;
			post.text = content;
			post.location = locationName;
			post.phoneForContact = phone;
			post.price = price;
			post.created = new Date();
			post.lastBid = new Date();
			
			post.ip = requestGlob.getHTTPServletRequest().getRemoteAddr();
			
			if (getVideoOnPost()) {
				try {
					URL vurl =  new URL(videoLink);
					String[] params = vurl.getQuery().split("&");
					for (String s : params) {
						String[] g = s.split("=");
						if (g[0].equals("v"))
							post.videoLink = "http://www.youtube.com/v/"+g[1];
					}
				} catch (Exception e) {
					post.videoLink = "";
				}
			}
			post.subcategory_id = subcategory.id;
			post.user_id = authenticator.getLoggedUser().id;
			postDAO.add(post);
			
			if (settingsDAO.getSettings().allowPicturesOnPost) {
				int size = uploadedFiles.size()<settingsDAO.getSettings().picturesPerPost?uploadedFiles.size():settingsDAO.getSettings().picturesPerPost;
				
				for (int i = 0; i < size; i++) {
					String instaLocation = settingsDAO.getSettings().imageURL+"/"+post.post_id+"_image_"+i+uploadedFiles.get(i).getFileName().substring(uploadedFiles.get(i).getFileName().length()-4);
					File picture = new File(instaLocation);
					//uploadedFiles.get(i).
					uploadedFiles.get(i).write(picture);
					ImageUtil.generateAndSaveWatermark(picture, settingsDAO.getSettings().websiteTitle);
					postDAO.addImageForPostID(post.post_id, post.post_id+"_image_"+i+uploadedFiles.get(i).getFileName().substring(uploadedFiles.get(i).getFileName().length()-4));
				}
				
				uploadedFiles = new ArrayList<UploadedFile>();
			}
			submited = true;
			/*URL u = null;
			String ur = "";
			try {
				ur = bus.getBaseURL(false)+appGlob.getServletContext().getContextPath()+"/view/post/"+post.post_id;
				u = new URL(ur);
			} catch (MalformedURLException e) {
			
			}
			if (u != null)
				return u;
			return index;*/
		}
		
		subcatPage.set(subcategory.id);
		return subcatPage;
    }
	
	public boolean getShowImageUpdload() {
		return settingsDAO.getSettings().allowPicturesOnPost;
	}
	
	public boolean getShowCaptcha() {
		return settingsDAO.getSettings().showCaptchaOnPostAds;
	}
	
    @Persist
    @Property
    private List<UploadedFile> uploadedFiles;

    @Inject
    private ComponentResources resources;
    
    @Persist
    @Property
    private Long lastPostsID;

    void onActivate() {
        if (uploadedFiles == null)
            uploadedFiles = new ArrayList<UploadedFile>();
    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
        if (uploadedFile != null) {
            this.uploadedFiles.add(uploadedFile);
        }
    }
}