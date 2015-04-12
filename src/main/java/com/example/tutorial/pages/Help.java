/**
 * 
 */
package com.example.tutorial.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.SettingsDAO;

/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class Help {
	@Inject
	private SettingsDAO settingsDAO;
	
	public String getTitle() {
		return settingsDAO.getSettings().helpTitle;
	}
	public String getText() {
		return settingsDAO.getSettings().helpText;
	}
}