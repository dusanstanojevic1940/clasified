/**
 * 
 */
package com.example.tutorial.pages.add;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.Category;
import com.example.tutorial.entities.Subcategory;

/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class ChooseCategory {
	@Property
    private Category category;
	
	@Property
	private Subcategory subcategory;
	
	@Inject
	private CategoryDAO categoryDAO;
	@Inject
	private SubcategoryDAO subcategoryDAO;
	
	public List<Category> getCategories() {
		return categoryDAO.all();
	}
	
	public List<Subcategory> getCurrentSubcategories() {
		ArrayList<Subcategory> toRet = new ArrayList<Subcategory>();
		for (Subcategory s : subcategoryDAO.getByCategory(category.id))
			if (s.showOnTopPage)	
				toRet.add(s);
		return toRet;
	}
}