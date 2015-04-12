/**
 * 
 */
package com.example.tutorial.pages.admin.delete;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.pages.admin.view.Users;

/**
 * @author dusanstanojevic
 *
 */
public class User {
	@Inject 
	private UserDAO userDAO;
	
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
		com.example.tutorial.entities.User user = userDAO.getByID(id);
		if ( user != null)
			userDAO.remove(user);
		return Users.class;
	}
}