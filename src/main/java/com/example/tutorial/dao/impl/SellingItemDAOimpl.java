package com.example.tutorial.dao.impl;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.SellingItemDAO;
import com.example.tutorial.entities.SellingItem;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class SellingItemDAOimpl implements SellingItemDAO {
	@Inject
	private Session session;
	
	@SuppressWarnings("unchecked")
	@CommitAfter
	public List<SellingItem> all() {
		return session.createCriteria(SellingItem.class).list();
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public SellingItem get(long id) {
		List<SellingItem> t = session.createCriteria(SellingItem.class).add(Restrictions.eq("id", id)).list();
		return t.get(0);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public void delete(long id) {
		List<SellingItem> t = session.createCriteria(SellingItem.class).add(Restrictions.eq("id", id)).list();
		session.delete(t.get(0));
	}
	
	@CommitAfter
	public void save(SellingItem toSave) {
		session.persist(toSave);
	}

	@CommitAfter
	public void delete(SellingItem toDel) {
		session.delete(toDel);
	}

	@CommitAfter
	public void saveOrUpdate(SellingItem toSave) {
		session.saveOrUpdate(toSave);
	}
}
