/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.entities.Category;
import com.example.tutorial.util.CSVAttachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Categories {

	@Property
	private Category category;
	
	@Inject
	private CategoryDAO categoryDAO;

	@Property
	@ActivationRequestParameter(value = "search")
    private String search;

	@Inject
    private PageRenderLinkSource pageRenderLinkSource;
	
	public List<com.example.tutorial.entities.Category> getCategories() {
		 if (search == null || search.equals(""))
				return categoryDAO.all();
			else {
				ArrayList<Category> toRet = new ArrayList<Category>();
				
				String[] t = search.split(" ");
				for (Category u : categoryDAO.all()) {
					for (String s : t) {
						if (u.name.contains(s)) {
							toRet.add(u);
							break;
						} else if ((u.id+"").contains(s)) {
							toRet.add(u);
							break;
						}
					}
				}
				return toRet;
			}
	}
	/*
	Object onActionFromDelete(long id) {
        Category user = categoryDAO.getByID(id);
        if (user != null)
            categoryDAO.remove(user);
        return this;
    }
	*/
	@OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
	public Object onSuccess() {
		Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
    	return link;
	}
	
	@OnEvent(value = EventConstants.SUCCESS, component = "downloadForm")
	public StreamResponse onSubmit() throws IOException {
		String toReturn = "";
		
		toReturn += "name,";
		toReturn += "id,";
		toReturn += "onFrontPage\r\n";
		
		for (Category po : getCategories()) {
			toReturn += po.name +",";
			toReturn += po.id +",";
			toReturn += po.onFrontPage+"\r\n";
		}
		
		InputStream in = IOUtils.toInputStream(toReturn, "UTF-8");
		
		return new CSVAttachment(in, "Categories-"+(new Date()));
	}
}