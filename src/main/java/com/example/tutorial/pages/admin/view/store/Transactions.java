/**
 * 
 */
package com.example.tutorial.pages.admin.view.store;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.entities.Transaction;

/**
 * @author dusanstanojevic
 *
 */
@AdminPage
public class Transactions {
	@Inject
	private com.example.tutorial.dao.TransactionsDAO transactionsDAO;
	
	
	public List<Transaction> getTransactions() {
		return transactionsDAO.all();
	}
}