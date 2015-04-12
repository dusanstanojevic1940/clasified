/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.entities.Settings;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class EditHomePage {
	@Property
    @Persist
    private boolean showAll;
	
	@Inject
	private CategoryDAO categoryDAO;
	
	@Property
	private String name;
	
	@Inject
	private SettingsDAO settingsDAO;
	
	public void onActivate() {
		name = settingsDAO.getSettings().singleCategoryURL;
	}
	
	public List<String> getCategories() {
    	ArrayList<String> toRet = new ArrayList<String>();
    	for(com.example.tutorial.entities.Category c : categoryDAO.all())
    		toRet.add(c.name);
    	return toRet;
    }    
	
	Object onSuccess() {
    	Settings settings = settingsDAO.getSettings();
    	
    	settings.singleCategoryHome = showAll;
    	settings.singleCategoryURL = name;
    	
    	settingsDAO.update(settings);
    	
    	return this;
    }
}