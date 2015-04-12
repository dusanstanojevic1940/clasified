/**
 * 
 */
package com.example.tutorial.pages.admin.create;

import org.apache.tapestry5.annotations.Property;

import com.example.tutorial.annotations.AnonymousAccess;

/**
 * @author dusanstanojevic
 *
 */
@AnonymousAccess
public class APIPostResponse {
	@Property
	private String id;
	
	/*
	@Property
	@ActivationRequestParameter
	private String title;
	@Property
	@ActivationRequestParameter
	private String text;
	*/
	public void setID(String id) {
		this.id = id;
	}
	
	public void onActivate(String id) {
		this.id = id;
	}
	
	public String onPassivate() {
		return id;
	}
}