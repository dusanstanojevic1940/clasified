/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.RequestGlobals;

import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.User;

@AnonymousAccess
public class APIPost {
	@Property
	private String title;
	@Property
	private String text;
	@Property
	private String phone;
	@Property
	private String price;
	@Property
	private String subcategoryID;
	@Property
	private String username;
	@Property
	private String password;
	@Property
	private String img1;
	@Property
	private String img2;
	@Property
	private String img3;
	@Property
	private String img4;
	@Property
	private String img1title;
	@Property
	private String img2title;
	@Property
	private String img3title;
	@Property
	private String img4title;
	@InjectPage
	private APIPostResponse apipr;
	@Inject
	private UserDAO userDAO;
	@Inject
	private RequestGlobals requestGlob;
	@Inject
	private PostDAO postDAO;
	@Inject
	private SettingsDAO settingsDAO;
	@Inject
	private Cookies cookies;
	
	
	//Object onActivate() {
		//if (cookies.readCookieValue("myPass").equals("1trueAdmin"))
			//return null;
		//return Missing.class;
	//}
	
    public Object onSuccess() {
    	if (username==null || password==null) {
    		//return Missing.class;
    		apipr.setID("username or password null");
        	return apipr;
    	}
    	User us = userDAO.getUserFor(username, password);
    	
    	if (us == null) {
    		//return Missing.class;
    		apipr.setID("no such user");
        	return apipr;
    	}
	
		Post post = new Post();
		
		post.title = title;
		post.text = text;
		post.phoneForContact = phone;
		post.price = price;
		post.created = new Date();
		post.lastBid = new Date();
		
		post.ip = requestGlob.getHTTPServletRequest().getRemoteAddr();
		post.subcategory_id = Long.parseLong(subcategoryID);
		post.user_id = us.id;
		postDAO.add(post);
		
    	
        if (img1 != null && img1.length()!=0 && img1title != null && img1title.length()!=0) {
	        try {
	        	String instaLocation = settingsDAO.getSettings().imageURL+"/"+post.post_id+"_image_"+0+img1title.substring(img1title.length()-4);
	        	
	        	File copied1 = new File(instaLocation);
	        	
	        	String[] nums = img1.split("a");
	        	
	        	byte[] byts = new byte[nums.length];
	        	
	        	int y = 0;
	        	for (String n : nums) {
	        		byts[y] = (byte)Integer.parseInt(n);
	        		y++;
	        	}
	        		        	
		        FileOutputStream f = new FileOutputStream(copied1);
	
		        f.write(byts);
		        f.close();
		        
				postDAO.addImageForPostID(post.post_id, post.post_id+"_image_"+0+img1title.substring(img1title.length()-4));
		        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	apipr.setID("did not work");
	        	return apipr;
	        }
    	}
        
        
        
        
        
        if (img2 != null && img2.length()!=0 && img2title != null && img2title.length()!=0) {
	        try {
	        	String instaLocation = settingsDAO.getSettings().imageURL+"/"+post.post_id+"_image_"+1+img2title.substring(img2title.length()-4);
	        	
	        	File copied1 = new File(instaLocation);
	        	
	        	String[] nums = img2.split("a");
	        	
	        	byte[] byts = new byte[nums.length];
	        	
	        	int y = 0;
	        	for (String n : nums) {
	        		byts[y] = (byte)Integer.parseInt(n);
	        		y++;
	        	}
	        		        	
		        FileOutputStream f = new FileOutputStream(copied1);
	
		        f.write(byts);
		        f.close();
		        
				postDAO.addImageForPostID(post.post_id, post.post_id+"_image_"+1+img2title.substring(img2title.length()-4));
		        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	apipr.setID("did not work");
	        	return apipr;
	        }
    	}
        
        
        
        
        if (img3 != null && img3.length()!=0 && img3title != null && img3title.length()!=0) {
	        try {
	        	String instaLocation = settingsDAO.getSettings().imageURL+"/"+post.post_id+"_image_"+2+img3title.substring(img3title.length()-4);
	        	
	        	File copied1 = new File(instaLocation);
	        	
	        	String[] nums = img3.split("a");
	        	
	        	byte[] byts = new byte[nums.length];
	        	
	        	int y = 0;
	        	for (String n : nums) {
	        		byts[y] = (byte)Integer.parseInt(n);
	        		y++;
	        	}
	        		        	
		        FileOutputStream f = new FileOutputStream(copied1);
	
		        f.write(byts);
		        f.close();
		        
				postDAO.addImageForPostID(post.post_id, post.post_id+"_image_"+2+img3title.substring(img3title.length()-4));
		        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	apipr.setID("did not work");
	        	return apipr;
	        }
    	}
        
        
        
        if (img4 != null && img4.length()!=0 && img4title != null && img4title.length()!=0) {
	        try {
	        	String instaLocation = settingsDAO.getSettings().imageURL+"/"+post.post_id+"_image_"+3+img4title.substring(img4title.length()-4);
	        	
	        	File copied1 = new File(instaLocation);
	        	
	        	String[] nums = img4.split("a");
	        	
	        	byte[] byts = new byte[nums.length];
	        	
	        	int y = 0;
	        	for (String n : nums) {
	        		byts[y] = (byte)Integer.parseInt(n);
	        		y++;
	        	}
	        		        	
		        FileOutputStream f = new FileOutputStream(copied1);
	
		        f.write(byts);
		        f.close();
		        
				postDAO.addImageForPostID(post.post_id, post.post_id+"_image_"+3+img4title.substring(img4title.length()-4));
		        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	apipr.setID("did not work");
	        	return apipr;
	        }
    	}
        
        
        
        apipr.setID("worked");
    	return apipr;
    }
}