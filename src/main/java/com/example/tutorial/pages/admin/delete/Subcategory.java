/**
 * 
 */
package com.example.tutorial.pages.admin.delete;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.pages.admin.view.Subcategories;

/**
 * @author dusanstanojevic
 *
 */
public class Subcategory {
	@Inject 
	private SubcategoryDAO subcategoryDAO;
	
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
		com.example.tutorial.entities.Subcategory user = subcategoryDAO.getByID(id);
		if ( user != null)
			subcategoryDAO.remove(user);
		return Subcategories.class;
	}
}