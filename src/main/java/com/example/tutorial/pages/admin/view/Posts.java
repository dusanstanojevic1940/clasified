/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.TopAdOn;
import com.example.tutorial.entities.User;
import com.example.tutorial.util.CSVAttachment;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Posts {
	@Property
	private Post post;
	
	@Inject
	private PostDAO postDAO;
	
	@Property
	@ActivationRequestParameter(value = "search")
    private String search;
	 
	public List<com.example.tutorial.entities.Post> getPosts() {
		List<Post> p;
		if (userId == null || userId.equals(""))
			p = postDAO.all();
		else
			p = userDAO.getMyAdsPosts(userId);
		
		if (search == null || search.equals(""))
			return p;
		else {
			ArrayList<Post> toRet = new ArrayList<Post>();
			
			String[] t = search.split(" ");
			for (Post u : p) {
				for (String s : t) {
					if (u.title.contains(s)) {
						toRet.add(u);
						break;
					} else if ((u.post_id+"").contains(s)) {
						toRet.add(u);
						break;
					} else if (u.text != null && u.text.contains(s)) {
						toRet.add(u);
						break;
					}
				}
			}
			return toRet;
		}
	}
	
	@Inject
	private UserDAO userDAO;
	
	@Component
    private Form deleteForm;
	
	@Property
	private String olderThan;

	@Property
	private String olderThanBump;
	
	public Object onSubmitFromDeleteForm() {
        int olderThanToDel = 0;
		try {
        	olderThanToDel = Integer.parseInt(olderThan);
        }
        catch (Exception ex) {
            deleteForm.recordError("What you have entered was invalid");
            return null;
        }

		for (Post p : postDAO.all()) {
			long diff = (new Date()).getTime() - p.created.getTime();
			if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>olderThanToDel)
				postDAO.delete(p.post_id);
		}
		
        return this;
    }
	
	@OnEvent(value=EventConstants.ACTION, component="viewUser")
	void viewUser(long id) {
		String message = "";
		User u = userDAO.getByID(postDAO.getUser(id));
		
		message+="Username: "+u.username+";  ";
		message+="Email: "+u.email+";  ";
		message+="Coins: "+u.coin+";  ";
		message+="Phone: "+u.phone+";  ";
		
		final String finalMessage = message;
		
		ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
			
			public void run(JavaScriptSupport jss) {
				jss.addScript("alert(\""+finalMessage+"\");");
				
			}
		});
	}
	
	 @Inject
	 private AjaxResponseRenderer ajaxResponseRenderer;

	 @Property
	 private Long userId;
	 
	 void onActivate(Long userId) {
		 this.userId = userId;
	 }
	 
	 Long onPassivate() {
		 return userId;
	 }
	
	
	public boolean isTopAdToday(long id) {
		List<TopAdOn> dates = postDAO.getDatesForPostID(id);
		
		Date today = new Date();
		
		for (TopAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public Date topAdUntil(long id) {
		if (postDAO.getLatestDateForTopAd(id)==null)
			return null;
		return postDAO.getLatestDateForTopAd(id);
	}
	
	@Inject
    private PageRenderLinkSource pageRenderLinkSource;
	
	@OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
	public Object onSuccess() {
		Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
    	return link;
	}
	
	
	@OnEvent(value = EventConstants.SUCCESS, component = "deleteBumpForm")
	public Object deleteByBump() {
		int olderThanToDel = 0;
		try {
        	olderThanToDel = Integer.parseInt(olderThanBump);
        }
        catch (Exception ex) {
            deleteForm.recordError("What you have entered was invalid");
            return null;
        }

		for (Post p : postDAO.all()) {
			long diff = (new Date()).getTime() - p.lastBid.getTime();
			if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>olderThanToDel)
				postDAO.delete(p.post_id);
		}
		
        return this;
	}
	
	
	
	@OnEvent(value = EventConstants.SUCCESS, component = "downloadForm")
	public StreamResponse onSubmit() throws IOException {
		String toReturn = "";
		
		toReturn += "title,";
		toReturn += "text,";
		toReturn += "price,";
		toReturn += "post_id,";
		toReturn += "phoneForContact\r\n";
		
		for (Post po : getPosts()) {
			toReturn += po.title +",";
			toReturn += po.text +",";
			toReturn += po.price +",";
			toReturn += po.post_id +",";
			toReturn += po.phoneForContact+"\r\n";
		}
		
		InputStream in = IOUtils.toInputStream(toReturn, "UTF-8");
		
		return new CSVAttachment(in, "Posts-"+(new Date()));
	}
	
	
	
	
	public String daysSincePosted(long id) {
		Post p = postDAO.getByID(id);
		long diff = (new Date()).getTime() - p.lastBid.getTime();
		return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))+"D";
		/*try {
			if (1==1)
				throw new RuntimeException("HERE");
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		//return "DUSAN";
	}
	
	
	
	
	
	public boolean isFeaturedAdToday(long id) {
		List<FeaturedAdOn> dates = postDAO.getFeaturedOnByPostID(id);
		
		Date today = new Date();
		
		for (FeaturedAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public Date featuredAdUntil(long id) {
		if (postDAO.getLatestDateForFeaturedAd(id)==null)
			return null;
		return (DateUtils.addDays(postDAO.getLatestDateForFeaturedAd(id), 1));
	}
}
