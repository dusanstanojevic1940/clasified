package com.example.tutorial.pages.admin.create;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.pages.admin.AdminMain;
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
    private AdminMain adminMain;
    
    @InjectPage
    private Posts postPage;
 
    Object onSuccess() {
    	//if (postService.addIfNotTaken(post))
    		//return adminMain;
    	//else 
    		//return postPage;
    	postDAO.add(post);
    	return postPage;
    }
}