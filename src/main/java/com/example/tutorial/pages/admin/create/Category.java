/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.pages.admin.AdminMain;
import com.example.tutorial.pages.admin.view.Categories;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Category {
	@Property
    private com.example.tutorial.entities.Category category;
 
    @Inject
    private CategoryDAO categoryDAO;
 
    @InjectPage
    private AdminMain adminMain;
    
    @InjectPage
    private Categories categoryPage;
 
    Object onSuccess() {
    	categoryDAO.add(category);
    	return categoryPage;
    }
}