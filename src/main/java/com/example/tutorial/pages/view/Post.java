/**
 * 
 */
package com.example.tutorial.pages.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.TopAdOn;
import com.example.tutorial.pages.Index;
import com.example.tutorial.pages.error.Missing;
import com.example.tutorial.services.Authenticator;

@AnonymousAccess
public class Post {
	@Property
	private com.example.tutorial.entities.Post post;
	
	@Inject
	private SubcategoryDAO subcategoryDAO;
	
	@Inject
	private SettingsDAO settingsDAO;
	
	@Inject
	private PostDAO postDAO;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private Authenticator authenticator;
	
	public String getWebsiteName() {
    	return settingsDAO.getSettings().websiteTitle;
    }
	
	Object onActivate(Long id) {
		this.post = postDAO.getByID(id);
		if (this.post == null)
			return Missing.class;
		return null;
	}
	
	public String getLastTopAdDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(postDAO.getLatestDateForTopAd(post.post_id));
	}
	
	public String getLastFeaturedAdDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(postDAO.getLatestDateForFeaturedAd(post.post_id));
	}
	
	public String getWebsiteTitle() {
		return settingsDAO.getSettings().websiteTitle;
	}
	
	long onPassivate() {
		if (post!=null)
			return this.post.post_id;
		return 0;
	}
	
	public String getPostsPrice() {
		return post.price;
	}
	
	@InjectPage
	private Index index;
	
	public boolean hasValidLocation() {
		return post.location != null && !post.location.equals("");
	}
	
	public boolean showIP() {
		return settingsDAO.getSettings().showIP;
	}
	
	@Property
	private String instaLocation; 
	
	public List<String> getPostImages() {
		List<String> toCopy = postDAO.getImagesForPostID(post.post_id);
		
		ArrayList<String> toReturn = new ArrayList<String>(toCopy.size());
		
		for (String s : toCopy)
			toReturn.add(settingsDAO.getSettings().imageOnWebsiteURL+"/"+s);
		
		return toReturn;
	}
	
	@OnEvent(value=EventConstants.ACTION, component="delete")
	Object delete(Long id) {
		if (postDAO.getUser(post.post_id).equals(authenticator.getLoggedUser().id) || userDAO.getByID(authenticator.getLoggedUser().id).isAdmin) {
			postDAO.delete(post.post_id);
	    	return index;
		} else {
			return index;
		}
	}
	
	public String getDatePosted() {
		return new SimpleDateFormat("yyyy-MM-dd").format(post.created);
	}
	
	@OnEvent(value=EventConstants.ACTION, component="watchlist")
	void addWatchlist() {
		userDAO.addToWatchlist(post.post_id, authenticator.getLoggedUser().id);
	}
	
	public long getSubcategoryID() {
		return postDAO.getPostSubcategory(post.post_id).id;
	}
	
	public String getSubcategoryName() {
		return postDAO.getPostSubcategory(post.post_id).name;
	}
	
	@Environmental
	private JavaScriptSupport javaScriptSupport;
	
	public boolean isTopAdToday() {
		long id = post.post_id;
		List<TopAdOn> dates = postDAO.getDatesForPostID(id);
		
		Date today = new Date();
		
		for (TopAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public boolean isFeaturedAdToday() {
		long id = post.post_id;
		List<FeaturedAdOn> dates = postDAO.getFeaturedOnByPostID(id);
		
		Date today = new Date();
		
		for (FeaturedAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public boolean hasValidUrl() {
		return settingsDAO.getSettings().allowVideoOnPost && post.videoLink!=null && !post.videoLink.equals("");
	}
	
	public boolean getThumb() {
		return isFeaturedAdToday() || isTopAdToday();
	}
}