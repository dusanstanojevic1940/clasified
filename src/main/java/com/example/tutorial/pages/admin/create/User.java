/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.pages.admin.AdminMain;
import com.example.tutorial.pages.admin.view.Users;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class User {
    @Property
    private com.example.tutorial.entities.User user;
 
    @Inject
    private UserDAO userDAO;
 
    @InjectPage
    private AdminMain adminMain;
    
    @InjectPage
    private Users userPage;
 
    Object onSuccess() {
    	userDAO.addIfNotTaken(user);
    	return userPage;
    }
}