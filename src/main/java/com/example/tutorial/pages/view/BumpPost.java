/**
 * 
 */
package com.example.tutorial.pages.view;

import java.io.IOException;
import java.util.Date;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.pages.Index;
import com.example.tutorial.pages.error.Missing;

/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class BumpPost {
	@Inject
	private SettingsDAO settingsDAO;
	
	@Property
	private long post_id;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private Subcategory subcat;
	
	@Inject
	private PostDAO postDAO;
	
	@Inject
	private ApplicationGlobals appGlob;
	
	@Inject 
	private Response response;
	
	@Inject
	private PageRenderLinkSource renderLinkSource;
	
	Object onActivate(Long post_id) throws IOException {
		this.post_id = post_id;
		if (postDAO.getByID(post_id)==null)
			return Missing.class;
		
		if (!settingsDAO.getSettings().showCaptchaOnBumpPost) {
			com.example.tutorial.entities.Post post = postDAO.getByID(post_id);
			post.lastBid=new Date();
			postDAO.update(post);
			subcat.set(post.subcategory_id);
			return subcat;
		}
		return null;
	}
	
	long onPssivate() {
		return post_id;
	}
	
	Object onSuccess() {
		com.example.tutorial.entities.Post post = postDAO.getByID(post_id);
		post.lastBid=new Date();
		postDAO.update(post);
    	return index;
    }
	
	long onPassivate() {
		return post_id;
	}
	
	public boolean getShowCaptcha() {
		return settingsDAO.getSettings().showCaptchaOnBumpPost;
	}
}