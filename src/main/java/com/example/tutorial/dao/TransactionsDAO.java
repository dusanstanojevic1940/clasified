package com.example.tutorial.dao;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.Transaction;

public interface TransactionsDAO {	
	@CommitAfter
	public List<Transaction> all();

	@CommitAfter
	public void save(Transaction toSave);
	
	@CommitAfter
	public Transaction get(long id);
	
	@CommitAfter
	public Transaction getByToken(String token);
}
