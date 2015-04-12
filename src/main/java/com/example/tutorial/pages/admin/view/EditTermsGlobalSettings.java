/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.entities.Settings;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class EditTermsGlobalSettings {
	@Inject
	private SettingsDAO settingsDAO;
	
	@Property
	private Settings settings = settingsDAO.getSettings();
	
	Object onSuccess() {
		settingsDAO.update(settings);
		return this;
	}
}