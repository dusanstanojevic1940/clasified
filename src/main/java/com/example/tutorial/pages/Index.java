package com.example.tutorial.pages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.Category;
import com.example.tutorial.entities.Subcategory;

/**
 * Start page of application tutorial1.
 */

@AnonymousAccess
public class Index
{	
	@Inject
	private SettingsDAO settingsDAO;
	
	public String getTitle() {
		return settingsDAO.getSettings().websiteTitle;
	}
	
	public String getMainHeaderTitle() {
		return settingsDAO.getSettings().mainHeaderTitle;
	}
	
	@Inject
	private Response response;
	
	Object onActivate() {
		if (settingsDAO.getSettings().singleCategoryHome) {
			try {
				return new URL(settingsDAO.getSettings().singleCategoryURL);
			} catch (IOException e) {
				return null;
			}
		} else
			return null;
	}
	
	@Inject
	private SubcategoryDAO subcategoryDAO;
	
	@Property
    private Category category;
	
	@Property
	private Subcategory subcategory;
	
	@Inject
	private CategoryDAO categoryDAO;
	
	public List<Category> getCategories() {
		return categoryDAO.onFrontPage();
	}
	
	public List<Subcategory> getCurrentSubcategories() {
		ArrayList<Subcategory> toRet = new ArrayList<Subcategory>();
		for (Subcategory s : subcategoryDAO.getByCategory(category.id))
			if (s.showOnTopPage)	
				toRet.add(s);
		return toRet;
	}
}
