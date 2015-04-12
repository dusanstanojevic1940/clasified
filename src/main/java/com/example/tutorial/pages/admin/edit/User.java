/**
 * 
 */
package com.example.tutorial.pages.admin.edit;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.pages.admin.AdminMain;
import com.example.tutorial.pages.admin.view.Users;

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
 
    @Property
    private Long id;
    
    void onActivate(Long id) {
    	this.id = id;
		this.user = userDAO.getByID(id);
	}
	
	Long onPassivate() {
		return id;
	}
    
    Object onSuccess() {
    	userDAO.saveOrUpdate(user);
    	return userPage;
    }
}