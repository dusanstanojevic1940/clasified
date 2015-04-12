package com.example.tutorial.pages.add;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.BaseURLSource;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.PostKind;
import com.example.tutorial.pages.Index;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.ImageUtil;

public class EditPost {
	@Inject
	private Authenticator authenticator;
	
	@Inject
	private PostDAO postDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private SettingsDAO settingsDAO;

	@Inject
	private BaseURLSource bus;
	
	@Inject
	private RequestGlobals requestGlob;
	
	@Inject
	private ApplicationGlobals appGlob;
	
	@Property
	private long id;
	
	@Property
	private String imageName;
	
	@InjectComponent
    private Zone zone;
	
	public boolean getShowLocation() {
		return settingsDAO.getSettings().showLocationOnPostAds;
	}
	
	public boolean getOfferingWanted() {
		return settingsDAO.getSettings().offeringWanted;
	}
	
	Object onActivate(Long id) {
		this.id = id;
		
		//uploadedFiles = new ArrayList<UploadedFile>();
		
		if (uploadedFiles == null)
			uploadedFiles = new ArrayList<UploadedFile>();
		
		if (!userDAO.getByID(authenticator.getLoggedUser().id).isAdmin && !postDAO.getUser(id).equals(authenticator.getLoggedUser().id))
			return Index.class;
		
		if (lastPostsID == null) {
			lastPostsID = id;
		} else if (!lastPostsID.equals(id)) {
			uploadedFiles = new ArrayList<UploadedFile>();
			lastPostsID = id;
		}
		
		Post post = postDAO.getByID(id);
		
		title = post.title;
		if (post.kind!=null)
			selectedKind = post.kind.toString();
		content = post.text;
		price = post.price;
		phone = post.phoneForContact;
		locationName = post.location;
		videoLink = post.videoLink;
		return null;
	}
	
	Long onPassivate() {
		return id;
	}
	
	@Property
	private String videoLink;
	
	@Property
	@Validate("required,maxlength=85")
	private String title;
	
	
	@Property
	private String selectedKind;
	
	@Property
	@Validate("required,maxlength=120000")
	private String content;
	
	@Property
	@Validate("required,maxlength=5")
	private String price;

	@Property
	@Validate("required,maxlength=25")
	private String phone;
	
	@Property
	private String locationName;
	
	@InjectPage
	private Index index;
	

	public List<String> getValidKinds() {
		ArrayList<String> list = new ArrayList<String>(2);
		list.add(PostKind.OFERING.toString());
		list.add(PostKind.WANTED.toString());
		return list;
	}
	
	public boolean getVideoOnPost() {
		return settingsDAO.getSettings().allowVideoOnPost;
	}
	
	public boolean getShowCaptcha() {
		return settingsDAO.getSettings().showCaptchaOnPostAds;
	}
	
	public List<String> getPostImages() {
		List<String> toCopy = postDAO.getImagesForPostID(id);
		
		ArrayList<String> toReturn = new ArrayList<String>(toCopy.size());
		
		for (String s : toCopy)
			toReturn.add(s);
		
		return toReturn;
	}
	
	public boolean getShowUploadButton() {
		if (postDAO.getImagesForPostID(id).size()<settingsDAO.getSettings().picturesPerPost && settingsDAO.getSettings().allowPicturesOnPost)
			return true;
		return false;
	}
	
	Object onAction(String name) {
		postDAO.deleteImageForPost(id, name, settingsDAO.getSettings().imageURL);
		return zone;
	}
		
    @Persist
    @Property
    private List<UploadedFile> uploadedFiles;

    @Persist
    @Property
    private Long lastPostsID;
    
    @Inject
    private ComponentResources resources;

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
        if (uploadedFile != null) {
            this.uploadedFiles.add(uploadedFile);
        }
    }

	
	@OnEvent(value = EventConstants.SUCCESS, component = "PostForm")
    public Object editAd() {
		Post toUpdate = postDAO.getByID(id);
		
		if (selectedKind != null && settingsDAO.getSettings().offeringWanted)
			toUpdate.kind = selectedKind.equals(PostKind.OFERING.toString())?PostKind.OFERING:PostKind.WANTED;
		
		toUpdate.title = title;
		toUpdate.text = content;
		toUpdate.location = locationName;
		toUpdate.phoneForContact = phone;
		toUpdate.price = price;
		toUpdate.videoLink = videoLink;
		toUpdate.lastBid = new Date();
		postDAO.saveOrUpdate(toUpdate);
		
		if (settingsDAO.getSettings().allowPicturesOnPost) {
			for (UploadedFile f : uploadedFiles) {
				for (int i = 0; i < settingsDAO.getSettings().picturesPerPost; i++) {
					String instaLocation = settingsDAO.getSettings().imageURL+"/"+toUpdate.post_id+"_image_"+i+f.getFileName().substring(f.getFileName().length()-4);
					if (!postDAO.exists(toUpdate.post_id, toUpdate.post_id+"_image_"+i+f.getFileName().substring(f.getFileName().length()-4))) {
						File picture = new File(instaLocation);
						f.write(picture);
						ImageUtil.generateAndSaveWatermark(picture, settingsDAO.getSettings().websiteTitle);
						postDAO.addImageForPostID(toUpdate.post_id, toUpdate.post_id+"_image_"+i+f.getFileName().substring(f.getFileName().length()-4));
						break;
					}
				}
			}
			uploadedFiles = new ArrayList<UploadedFile>();
		}

		
		/*URL u = null;
		String ur = "";
		try {
			ur = bus.getBaseURL(false)+appGlob.getServletContext().getContextPath()+"/view/post/"+id;
			u = new URL(ur);
		} catch(MalformedURLException e) {
			/*e.printStackTrace();
			try {
				throw new RuntimeException(ur);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		if (u != null)
			return u;*/
		return index;
    }
}
