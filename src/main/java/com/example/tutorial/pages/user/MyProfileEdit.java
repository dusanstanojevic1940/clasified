/**
 * 
 */
package com.example.tutorial.pages.user;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.User;
import com.example.tutorial.services.Authenticator;

/**
 * @author dusanstanojevic
 *
 */
public class MyProfileEdit {
	@Inject
	private Authenticator authenticator;
	
	@Property
	private String newPhone;
	
	@Property
	private String newPassword;
	
	@Property
	private String newEmail;
	
	@Property
	private Form editForm;
	
	@InjectPage
	private MyProfile myP;
	
	@Inject
	private UserDAO userDAO;
	
	public String getUsername() {
		if (authenticator.getLoggedUser().username!=null)
			return authenticator.getLoggedUser().username;
		else
			return "";
	}
	
	public String getPhoneNumber() {
		if (authenticator.getLoggedUser().phone!=null)
			return authenticator.getLoggedUser().phone;
		else
			return "";
	}
	
	public String getEmail() {
		 String email = authenticator.getLoggedUser().email;
		 if (email==null)
			 return "";
		 int at = email.indexOf('@');
		 String toReturn = "";
		 for (int i = 0; i<email.length(); i++)
			 if (i<at)
				 toReturn+="*";
			 else
				 toReturn+=email.charAt(i);
		 
		 return toReturn;
	 }
	
	public Object onSubmitFromEditForm() {
		User toUpdate = authenticator.getLoggedUser();
        
		if (newEmail!=null && !newEmail.equals(""))
			toUpdate.email = newEmail;
        
        if (newPassword!=null && !newPassword.equals(""))
        	toUpdate.password = newPassword;
        
        if (newPhone!=null && !newPhone.equals(""))
        	toUpdate.phone = newPhone;
        
        userDAO.update(toUpdate);
        
		return myP;
    }
}