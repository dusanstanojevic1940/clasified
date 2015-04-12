/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.pages.admin.view.Subcategories;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Subcategory {
	@Property
    @Persist
    private boolean showAll;
	
	@Property
    private com.example.tutorial.entities.Subcategory subcategory;
 
    @Inject
    private SubcategoryDAO subcategoryDAO;
    
    @InjectPage
    private Subcategories subcategoryPage;
    
    @Property
    private String subcategoryName;
    
    @Property
    private String categoryName;
 
    @Inject
    private CategoryDAO categoryDAO;

    
    void onValidateFromCategoryName(String name) throws ValidationException {
    	if (name==null || name.equals(""))
    		throw new ValidationException("Should you not enter a Categry name?");
    	if (categoryDAO.get(name)==null)
    		throw new ValidationException("Should you not enter a valid Categry name?");
    }
    
    void onValidateFromSubcategoryName(String name) throws ValidationException {
        if (name==null || name.equals(""))
      	  	throw new ValidationException("Should you not enter a Subcategory name?");
    }
    
    public List<String> getCategories() {
    	ArrayList<String> toRet = new ArrayList<String>();
    	for(com.example.tutorial.entities.Category c : categoryDAO.all())
    		toRet.add(c.name);
    	return toRet;
    }    
    
    Object onSuccess() {
    	com.example.tutorial.entities.Subcategory toSave = new com.example.tutorial.entities.Subcategory();
    	toSave.name = subcategoryName;
    	com.example.tutorial.entities.Category cat = categoryDAO.get(categoryName);
        toSave.showOnTopPage=showAll;
        toSave.category_id = cat.id;
    	subcategoryDAO.add(toSave);
    	return subcategoryPage;
    }
}