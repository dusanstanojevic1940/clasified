/**
 * 
 */
package com.example.tutorial.components;

import org.apache.tapestry5.annotations.Parameter;

/**
 * @author dusanstanojevic
 *
 */
public class StoreInnerNavigation {
	@Parameter(required=true)
	private String selected;
 
	public String getStyle(String name) {
		if (selected.equals(name))
			return "text-decoration:underline;";
		else 
			return "";
	}
}