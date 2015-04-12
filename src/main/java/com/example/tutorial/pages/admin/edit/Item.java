/**
 * 
 */
package com.example.tutorial.pages.admin.edit;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SellingItemDAO;
import com.example.tutorial.pages.admin.view.store.Items;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Item {
	@Property
    private com.example.tutorial.entities.SellingItem item;
 
    @Inject
    private SellingItemDAO itemDAO;
    
    @InjectPage
    private Items itemsPage;
 
    void onActivate(Long id) {
		this.item = itemDAO.get(id);
	}
	
	long onPassivate() {
		return item.id;
	}
    
    Object onSuccess() {
    	itemDAO.saveOrUpdate(item);
    	return itemsPage;
    }
}