package com.example.tutorial.dao.impl;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.example.tutorial.dao.TransactionsDAO;
import com.example.tutorial.entities.Transaction;

public class TransactionsDAOimpl implements TransactionsDAO {	
	@Inject
	private Session session;
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Transaction> all() {
		return session.createCriteria(Transaction.class).list();
	}

	@CommitAfter
	public void save(Transaction toSave) {
		session.persist(toSave);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public Transaction get(long id) {
		List<Transaction> t = session.createCriteria(Transaction.class).add(Restrictions.eq("id", id)).list();
		if (t.size()!=0)
			return t.get(0);
		return null;
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public Transaction getByToken(String token) {
		List<Transaction> t = session.createCriteria(Transaction.class).add(Restrictions.eq("token", token)).list();
		if (t.size()!=0)
			return t.get(0);
		return null;
	}
}
