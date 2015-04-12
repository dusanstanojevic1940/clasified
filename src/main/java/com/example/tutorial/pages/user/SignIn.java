/**
 * 
 */
package com.example.tutorial.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.corelib.components.Form;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.pages.Index;

import org.apache.tapestry5.ioc.Messages;

import com.example.tutorial.security.AuthenticationException;
import com.example.tutorial.services.Authenticator;

/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class SignIn {
	//@Property
	//private String username;
	
	//@Property
	//private String password;
	/*
    @Inject
    private Authenticator authenticator;
    
    
    @Component
    private Form loginForm;
	
	public Object onSubmitFromLoginForm()
    {
        try
        {
            authenticator.login(username, password);
        }
        catch (AuthenticationException ex)
        {
            //loginForm.recordError(messages.get("error.login"));
            return null;
        }

        return Index.class;
    }*/
	
	

    @Property
    private String flashmessage;

    @Property
    private String username;

    @Property
    private String password;

    @Inject
    private Authenticator authenticator;

    @Component
    private Form loginForm;

    @Inject
    private Messages messages;


    public Object onSubmitFromLoginForm() {
        try {
            authenticator.login(username, password);
        } catch (AuthenticationException ex) {
            loginForm.recordError(messages.get("main"));
            return null;
        }

        return Index.class;
    }

    public String getFlashMessage() {
        return flashmessage;
    }

    public void setFlashMessage(String flashmessage) {
        this.flashmessage = flashmessage;
    }

}