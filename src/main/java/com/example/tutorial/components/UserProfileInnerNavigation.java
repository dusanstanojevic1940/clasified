/**
 * 
 */
package com.example.tutorial.components;

import org.apache.tapestry5.annotations.Parameter;

/**
 * @author dusanstanojevic
 *
 */
public class UserProfileInnerNavigation {
	@Parameter(required=true)
	private String selected;
 
	public String getClass(String name) {
		if (selected.equals(name))
			return "active";
		else 
			return "";
	}
}