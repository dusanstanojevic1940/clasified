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
public class Terms {
	@Inject
	private SettingsDAO settingsDAO;
	
	public String getTitle() {
		return settingsDAO.getSettings().termsAndConditionsTitle;
	}
	public String getSubtitle() {
		return settingsDAO.getSettings().termsAndConditionsSubtitle;
	}
	public String getWarnning() {
		return settingsDAO.getSettings().termsAndConditionsWarnning;
	}
	public String getFacts() {
		return settingsDAO.getSettings().termsAndConditionsFacts;
	}
	public String getLiability() {
		return settingsDAO.getSettings().termsAndConditionsLiabilityHeader;
	}
	
	public String getLiabilityFacts() {
		return settingsDAO.getSettings().termsAndConditionsLiabilityFacts;
	}
}