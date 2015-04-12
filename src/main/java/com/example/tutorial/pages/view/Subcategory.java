/**
 * 
 */
package com.example.tutorial.pages.view;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.Settings;
import com.example.tutorial.entities.Static;
import com.example.tutorial.entities.TopAdOn;
import com.example.tutorial.pages.error.Missing;
import com.example.tutorial.util.PostComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class Subcategory {
	@Property
	private com.example.tutorial.entities.Subcategory subcategory;
	
	@Property
	private com.example.tutorial.entities.Post currentPost;
	
	@Inject
	private SubcategoryDAO subcategoryDAO;
	
	@Inject
	private PostDAO postDAO;
	
	@Inject 
	private SettingsDAO settingsDAO;
	
	@Property
	private Settings sett;
	
	@Property
	List<Post>toRet;
	
	//@Inject
	//private Request request;
	
	private Long toBePassedIn;
	
	@Property
	List<Post> original;
	
	
	public void set (Long id) {
		toBePassedIn = id;
	}
	
	Object onActivate(Long id) {
		fimg = request.getContextPath()+"/layout/images/fire1.png";
		
		this.toBePassedIn = id;
		this.subcategory = subcategoryDAO.getByID(toBePassedIn);
		if (subcategory == null) {
			return Missing.class;
		}

		search=request.getParameter("search")==null?"":request.getParameter("search").toString();
		
		sett = settingsDAO.getSettings();
		
		original =  postDAO.getBySubcategory(subcategory.id);
		toRet = new ArrayList<Post>();
		
		List<Post> afterShow;
		if (show!=null && !show.equals("")) {
			afterShow = new ArrayList<Post>();
			for (Post p : original) {
				if (p.kind!=null && p.kind.toString().equals(show)) {
					afterShow.add(p);
				}
			}
		} else {
			afterShow = original;
		}
		
		
		for (Post p : afterShow) {
			if (search!=null && !search.equals("")) {
				String[] words = search.split(" ");
				for (String w : words) {
					if ((p.text!=null && p.text.toLowerCase().contains(w.toLowerCase()))||(p.title!=null && p.title.toLowerCase().contains(w.toLowerCase()))||(p.phoneForContact.toLowerCase()!=null && p.phoneForContact.contains(w.toLowerCase()))) {
						toRet.add(p);
						break;
					}
				}
			} else {
				toRet.add(p);
			} 
		}	
		Collections.sort(toRet, new PostComparator(postDAO));
		
		return null;
	}
	
	Long onPassivate() {
		return toBePassedIn;
	}
	
	public String getTimeSincePosted() {
		return Static.timeSince(currentPost.lastBid);
	}
	
	public List<com.example.tutorial.entities.Post> getPosts() {
		
		return toRet;
	} 
	
	@ActivationRequestParameter(value = "show")
    private String show;

    @Property
	private String search;

    @ActivationRequestParameter(value = "page")
    private Integer page;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    public Link set(String show, String search) {
        if (show!=null)
        	this.show = show;
        if (search!=null)
        	this.search = search;

        return pageRenderLinkSource.createPageRenderLink(this.getClass());
    }
    
    public List<com.example.tutorial.entities.Post> getPostsToShow() {
    	List<Post> toReturn = new ArrayList<Post>();
    	if (page == null)
    		page = 0;
    	for (int i = page*sett.postsPerPage; i<(page+1)*sett.postsPerPage; i++) {
    		if (i < getPosts().size())
    			toReturn.add(getPosts().get(i));
    	}
    	
    	return toReturn;
    }
    
    public boolean getHasPages() {
    	if (getPosts().size()/sett.postsPerPage>1)
    		return true;
    	if (getPosts().size()/sett.postsPerPage==1 && getPosts().size()%sett.postsPerPage>0)
    		return true;
    	return false;
    }
    
    public List<Integer> getAllPageNumbers() {
    	List<Integer> toRet = new ArrayList<Integer>();
    	
    	int size = getPosts().size()/sett.postsPerPage+1 +(getPosts().size()%sett.postsPerPage>0?1:0) ;
    	
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
    
    @OnEvent(value=EventConstants.ACTION, component="pageNavigation")
	Object changePage(int pageNum) {
    	Link link = this.set(show, search, pageNum-1);
    	return link;
	}
	
    @OnEvent(value=EventConstants.ACTION, component="pageNavigationNext")
	Object changeToNext() {
    	Link link = this.set(show, search, page+1);
    	return link;
	}
    
    public Link set(String show, String search, Integer page) {
        if (show!=null)
        	this.show = show;
        if (search!=null)
        	this.search = search;
        if (page!=null) {
        	if (page < getAllPageNumbers().size() )
        		this.page = page;
        	else 
        		this.page = 0;
        } else {
        	this.page = 0;
        }
        
        return pageRenderLinkSource.createPageRenderLink(this.getClass());
    }
	
	Object onSuccess() {
        Link link = this.set(null, "searchedFor This");
        return link;
    }
	
	@OnEvent(value=EventConstants.ACTION, component="offering")
	Object offering() {
		Link link = this.set("offering", null);
        return link;
	}
	
	@OnEvent(value=EventConstants.ACTION, component="wanted")
	Object wanted() {
		Link link = this.set("wanted", null);
        return link;
	}
	
	public boolean getIsTopAdToday(long id) {
		List<TopAdOn> dates = postDAO.getDatesForPostID(id);
		
		Date today = new Date();
		
		for (TopAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public boolean getIsFeaturedAdToday(long id) {
		List<FeaturedAdOn> dates = postDAO.getFeaturedOnByPostID(id);
		
		Date today = new Date();
		
		for (FeaturedAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}

	@Inject 
	private Request request; 
	
	public String getPostsImage() {
		List<String>imgsFpost = postDAO.getImagesForPostID(currentPost.post_id);
		if (imgsFpost.size()>0)
			return sett.imageOnWebsiteURL+"/"+imgsFpost.get(0);
		return request.getContextPath()+"/layout/images/default.jpg";
	}
	
	public String getBackgroundStyle(long id) {
		if (getIsFeaturedAdToday(id)) 	
		return
		"background: rgb(247,244,165); /* Old browsers */ background: -moz-linear-gradient(top,  rgba(247,244,165,1) 0%, rgba(255,249,140,1) 36%, rgba(252,244,174,1) 77%); /* FF3.6+ */ background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(247,244,165,1)), color-stop(36%,rgba(255,249,140,1)), color-stop(77%,rgba(252,244,174,1))); /* Chrome,Safari4+ */ background: -webkit-linear-gradient(top,  rgba(247,244,165,1) 0%,rgba(255,249,140,1) 36%,rgba(252,244,174,1) 77%); /* Chrome10+,Safari5.1+ */ background: -o-linear-gradient(top,  rgba(247,244,165,1) 0%,rgba(255,249,140,1) 36%,rgba(252,244,174,1) 77%); /* Opera 11.10+ */ background: -ms-linear-gradient(top,  rgba(247,244,165,1) 0%,rgba(255,249,140,1) 36%,rgba(252,244,174,1) 77%); /* IE10+ */ background: linear-gradient(to bottom,  rgba(247,244,165,1) 0%,rgba(255,249,140,1) 36%,rgba(252,244,174,1) 77%); /* W3C */ filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#f7f4a5', endColorstr='#fcf4ae',GradientType=0 ); /* IE6-9 */";

		return "";
	}
	
	private String fimg;
	
	public String getFireImage() {
		return fimg;
	}
	
	public String getPostsSecondImage() {
		List<String>currImg = postDAO.getImagesForPostID(currentPost.post_id);
		if (currImg.size()>1)
			return sett.imageOnWebsiteURL+"/"+currImg.get(1);
		if (postDAO.getImagesForPostID(currentPost.post_id).size()>0)
			return sett.imageOnWebsiteURL+"/"+currImg.get(0);
		return request.getContextPath()+"/layout/images/default.jpg";
	}
	
	public boolean getOfferingWanted() {
		return sett.offeringWanted;
	}
}