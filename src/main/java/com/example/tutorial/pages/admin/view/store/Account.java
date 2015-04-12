/**
 * 
 */
package com.example.tutorial.pages.admin.view.store;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.entities.PayPalAccount;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Account {
	@Property
    private PayPalAccount payPalAccount;
 
    @Inject
    private SettingsDAO settingsDAO;
 
    void onActivate() {
    	payPalAccount = settingsDAO.getPayPalAccount();
	}
	
    Object onSuccess() {
    	settingsDAO.updatePayPalAccount(payPalAccount);
    	return this;
    }
}