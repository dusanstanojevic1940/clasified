/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SellingItemDAO;
import com.example.tutorial.entities.SellingItem;
import com.example.tutorial.pages.admin.AdminMain;
import com.example.tutorial.pages.admin.view.store.Items;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Item {
	@Property
    private SellingItem item;
 
    @Inject
    private SellingItemDAO itemDAO;
 
    @InjectPage
    private AdminMain adminMain;
    
    @InjectPage
    private Items postPage;
 
    Object onSuccess() {
    	itemDAO.save(item);
    	return postPage;
    }
}