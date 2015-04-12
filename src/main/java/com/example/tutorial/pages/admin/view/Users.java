/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import org.apache.commons.io.IOUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.User;
import com.example.tutorial.util.CSVAttachment;
/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Users {
	@Property
	private User user;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private PostDAO postDAO;
	
	public List<com.example.tutorial.entities.User> getUsers() {
		if (search == null || search.equals(""))
			return userDAO.all();
		else {
			ArrayList<User> toRet = new ArrayList<User>();
			
			String[] t = search.split(" ");
			for (User u : userDAO.all()) {
				for (String s : t) {
					if (u.username.contains(s)) {
						toRet.add(u);
						break;
					} else if ((u.id+"").contains(s)) {
						toRet.add(u);
						break;
					} else if (u.phone != null && u.phone.contains(s)) {
						toRet.add(u);
						break;
					} else if (u.email != null && u.email.contains(s)) {
						toRet.add(u);
						break;
					}
				}
			}
			return toRet;
		}
	}
	/*
	Object onActionFromDeleteAllPosts(long id) {
        List<Post> posts = userDAO.getMyAdsPosts(id);
        for (Post p : posts)
        	postDAO.remove(p);
        return this;
    }
	
	Object onActionFromDelete(long id) {
        user = userDAO.getByID(id);
        if (user != null)
            userDAO.remove(user);
        return this;
    }
	*/
	@Property
	@ActivationRequestParameter(value = "search")
    private String search;
	
	@Inject
    private PageRenderLinkSource pageRenderLinkSource;
	
	@OnEvent(value = EventConstants.SUCCESS, component = "RegisterForm")
	public Object onSuccess() {
    	Link link = pageRenderLinkSource.createPageRenderLink(this.getClass());
    	return link;
	}

	@OnEvent(value = EventConstants.SUCCESS, component = "downloadForm")
	public StreamResponse onSubmit() throws IOException {
		//FileInputStream input = new FileInputStream("/var/lib/tomcat7/webapps/database/ads.h2.db");
        
		//InputStream input = new Scanner(System.in);
		String toReturn = "";
		
		toReturn += "username,";
		toReturn += "coin,";
		toReturn += "email,";
		toReturn += "phone,";
		toReturn += "id,";
		toReturn += "isAdmin\r\n";
		
		for (User us : getUsers()) {
			toReturn += us.username +",";
			toReturn += us.coin +",";
			toReturn += us.email +",";
			toReturn += us.phone +",";
			toReturn += us.id +",";
			toReturn += us.isAdmin +"\r\n";
		}
		
		InputStream in = IOUtils.toInputStream(toReturn, "UTF-8");
		
		return new CSVAttachment(in, "Users-"+(new Date()));
	}
}
