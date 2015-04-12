/**
 * 
 */
package com.example.tutorial.pages.admin.edit;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.pages.admin.view.Posts;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Post {
	@Property
    private com.example.tutorial.entities.Post post;
 
    @Inject
    private PostDAO postDAO;
    
    @InjectPage
    private Posts postsPage;
 
    void onActivate(Long id) {
		this.post = postDAO.getByID(id);
	}
	
	long onPassivate() {
		return post.post_id;
	}
    
    Object onSuccess() {
    	postDAO.saveOrUpdate(post);
    	return postsPage;
    }
}