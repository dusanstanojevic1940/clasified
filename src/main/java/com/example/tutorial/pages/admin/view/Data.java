/**
 * 
 */
package com.example.tutorial.pages.admin.view;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.services.CleanUpAndSaveService;
import com.example.tutorial.util.DBAttachment;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Data {
	@Inject
	private CleanUpAndSaveService cuass;
	
	public StreamResponse onSubmit() {
		return new DBAttachment(cuass.save());
	}
	
	public void onAction() {
		cuass.optimize();
	}
}