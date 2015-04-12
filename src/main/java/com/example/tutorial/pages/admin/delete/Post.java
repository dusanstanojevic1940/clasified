package com.example.tutorial.pages.admin.delete;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.pages.admin.view.Posts;

/**
 * @author dusanstanojevic
 *
 */
public class Post {
	@Inject 
	private PostDAO postDAO;

	@Property
	private long id;

	void onActivate(long id) {
		this.id = id;
	}

	long onPassivate() {
		return id;
	}

	@OnEvent(value=EventConstants.ACTION, component="yes")
	private Object onAction() {
		com.example.tutorial.entities.Post user = postDAO.getByID(id);
        if (user != null)
            postDAO.delete(id);
		return Posts.class;
	}
}