/**
 * 
 */
package com.example.tutorial.pages.admin.view.store;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SellingItemDAO;
import com.example.tutorial.entities.SellingItem;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Items {
	@Property
	private SellingItem item;
	
	@Inject
	private SellingItemDAO itemDAO;
	 
	public List<com.example.tutorial.entities.SellingItem> getItems() {
	    return itemDAO.all();
	}
	
	Object onActionFromDelete(long id) {
        item = itemDAO.get(id);
        if (item != null)
            itemDAO.delete(item);
        return this;
    }
}