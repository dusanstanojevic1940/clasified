/**
 * 
 */
package com.example.tutorial.pages.user;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.User;
import com.example.tutorial.pages.Index;
import com.example.tutorial.security.AuthenticationException;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.EmailValidator;

@AnonymousAccess
public class Register {
	@Persist
	@Validate("required")
	@Property 
	private String username;
	
	@Persist
	@Validate("required")
	@Property 
	private String password;
	
	@Persist
	@Validate("required")
	@Property
	private String email;
	
	@Inject
    private Authenticator authenticator;
	
	@InjectPage
	private Index index;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private SettingsDAO settingsDAO;

    @Component
    private Form registerForm;

    @Inject
    private Messages messages;
	
    public boolean getShowCaptcha() {
    	return settingsDAO.getSettings().showCaptchaOnUserRegistration;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
    public Object proceedSignup() {
    	EmailValidator e = new EmailValidator();
    	
    	if (!e.validate(email)) {
    		registerForm.recordError(messages.get("bademail"));
    		return null;
    	}
    	
    	User toAdd = new User();
    	toAdd.username = username;
    	toAdd.password = password;
    	toAdd.email = email;
    	
    	if (userDAO.all().size()==0)
    		toAdd.isAdmin = true;
    	
    	boolean registered = userDAO.addIfNotTaken(toAdd);

        if (!registered) {
            registerForm.recordError(messages.get("badname"));
            return null;
        }
        
        try {
            authenticator.login(username, password);
        }
        catch (AuthenticationException ex) {
            registerForm.recordError("Authentication process has failed");
            return this;
        }

        return index;
    }
}