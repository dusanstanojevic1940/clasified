/**
 * 
 */
package com.example.tutorial.pages.admin.delete;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.pages.admin.view.Categories;

/**
 * @author dusanstanojevic
 *
 */
public class Category {
	@Inject 
	private CategoryDAO categoryDAO;
	
	@Property
	private long id;
	
	void onActivate(long id) {
		this.id = id;
	}
	
	long onPassivate() {
		return id;
	}
	
	@OnEvent(value=EventConstants.ACTION, component="yes")
	private Object onAction() {
		com.example.tutorial.entities.Category user = categoryDAO.getByID(id);
        if (user != null)
            categoryDAO.remove(user);
		return Categories.class;
	}
}