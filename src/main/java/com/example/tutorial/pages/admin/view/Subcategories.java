/**
 * 
 */
package com.example.tutorial.pages.admin.view;

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

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.Subcategory;
import com.example.tutorial.util.CSVAttachment;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Subcategories {
	@Inject
	private SubcategoryDAO subcategoryDAO;

	@Property
	@ActivationRequestParameter(value = "search")
    private String search;
	
	public List<com.example.tutorial.entities.Subcategory> getSubcategories()
	{
	    if (search == null || search.equals(""))
			return subcategoryDAO.all();
		else {
			ArrayList<Subcategory> toRet = new ArrayList<Subcategory>();
			
			String[] t = search.split(" ");
			for (Subcategory u : subcategoryDAO.all()) {
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

	@Property
	private Subcategory subcategory;
	/*
	Object onActionFromDelete(long id) {
        Subcategory user = subcategoryDAO.getByID(id);
        if (user != null)
            subcategoryDAO.remove(user);
        return this;
    }
	*/
	@Inject
    private PageRenderLinkSource pageRenderLinkSource;
	
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
		toReturn += "showOnTopPage\r\n";
		
		for (Subcategory po : getSubcategories()) {
			toReturn += po.name +",";
			toReturn += po.id +",";
			toReturn += po.showOnTopPage+"\r\n";
		}
		
		InputStream in = IOUtils.toInputStream(toReturn, "UTF-8");
		
		return new CSVAttachment(in, "Subcategories-"+(new Date()));
	}
}