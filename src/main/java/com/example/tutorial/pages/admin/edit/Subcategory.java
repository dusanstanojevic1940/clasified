/**
 * 
 */
package com.example.tutorial.pages.admin.edit;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.pages.admin.view.Subcategories;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Subcategory {
	@Property
    private com.example.tutorial.entities.Subcategory subcategory;
 
    @Inject
    private SubcategoryDAO subcategoryDAO;
    
    @InjectPage
    private Subcategories subcategoriesPage;
 
    void onActivate(Long id) {
		this.subcategory = subcategoryDAO.getByID(id);
	}
	
	long onPassivate() {
		return subcategory.id;
	}
    
    Object onSuccess() {
    	subcategoryDAO.update(subcategory);
    	return subcategoriesPage;
    }
}