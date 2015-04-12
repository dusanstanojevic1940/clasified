/**
 * 
 */
package com.example.tutorial.pages.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.Static;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.PostComparator;

/**
 * @author dusanstanojevic
 *
 */
public class MyWatchlist {
	@Inject
	private PostDAO postDAO;
	
	@Inject 
	private UserDAO userDAO;
	
	@Inject
	private Authenticator authenticator;
	
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
	
	@Property
	private Post post;
	
	@Inject
	private SettingsDAO settingsDAO;
	
	@Inject
	private Request request;
	
	public String getPostsImage(long id) {
		if (postDAO.getImagesForPostID(id).size()>0)
			return settingsDAO.getSettings().imageOnWebsiteURL+"/"+postDAO.getImagesForPostID(id).get(0);
		return request.getContextPath()+"/layout/images/default.jpg";
	}
	
	public String getTimeSincePosted() {
		return Static.timeSince(post.created);
	}
	
	public List<Post> getMyPosts() {
		return userDAO.getMyWatchlistPosts(authenticator.getLoggedUser().id);
	}

	void onAction(Long id) {
    	//Post toUpdate = postDAO.getByID(id);
    	//toUpdate.liked.remove(userDAO.getByID(authenticator.getLoggedUser().id));
    	//postDAO.update(toUpdate);
		//(authenticator.getLoggedUser()).watchlist = (userDAO.getByID((authenticator.getLoggedUser()).id)).watchlist;
		userDAO.removeFromWatchlist(authenticator.getLoggedUser().id, id);
	}
	
	public boolean getNoAds() {
		return getMyPosts().size() == 0;
	}
    
    
    
	public List<com.example.tutorial.entities.Post> getPostsToShow() {
    	List<Post> toReturn = new ArrayList<Post>();
    	if (page == null)
    		page = 0;
    	for (int i = page*settingsDAO.getSettings().postsPerPage; i<(page+1)*settingsDAO.getSettings().postsPerPage; i++) {
    		if (i < getMyPosts().size())
    			toReturn.add(getMyPosts().get(i));
    	}
    	Collections.sort(toReturn, new PostComparator(postDAO));
    	return toReturn;
    }
	
	@ActivationRequestParameter(value = "page")
    private Integer page;
	
    public boolean getHasPages() {
    	if (getMyPosts().size()/settingsDAO.getSettings().postsPerPage>1)
    		return true;
    	if (getMyPosts().size()/settingsDAO.getSettings().postsPerPage==1 && getMyPosts().size()%settingsDAO.getSettings().postsPerPage>0)
    		return true;
    	return false;
    }
    
    public List<Integer> getAllPageNumbers() {
    	List<Integer> toRet = new ArrayList<Integer>();
    	
    	int size = getMyPosts().size()/settingsDAO.getSettings().postsPerPage+1 +(getMyPosts().size()%settingsDAO.getSettings().postsPerPage>0?1:0) ;
    	
    	for (int i = 1; i < size; i++) {
    		toRet.add(i);
    	}
    	
    	return toRet;
    }
    
    @Property
    private int loopPageNumber;
    
    public String getClassForPage(int page) {
    	if (this.page == null)
    		return "page "+(page==1?"selected":"");
    	if (page == this.page+1)
    		return "page selected";
    	return "page";
    }
    
    public int getCurrentPage() {
    	if (page == null)
    		return 0;
    	return page;
    }
    
    public Integer getFormated(Integer p) {
        if (p < getAllPageNumbers().size() )
        	return p;
        else 
        	return 0;
    }
    
    @OnEvent(value=EventConstants.ACTION, component="pageNavigation")
	Object changePage(int pageNum) {
    	if (page == null)
    		page = 0;
    	page = getFormated(pageNum-1);
    	Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
    	return link;
	}
	
    @OnEvent(value=EventConstants.ACTION, component="pageNavigationNext")
	Object changeToNext() {
    	if (page == null)
    		page = 0;
    	page = getFormated(page+1);
    	Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
    	return link;
	}
}