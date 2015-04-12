package com.example.tutorial.dao.impl;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.example.tutorial.dao.CategoryDAO;
import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.Category;
import com.example.tutorial.entities.Subcategory;

public class CategoryDAOimpl implements CategoryDAO {
	@Inject
	private Session session;
	
	@Inject
	private PostDAO postDAO;
	
	@Inject
	private SubcategoryDAO subcategoryDAO;
	
	@SuppressWarnings("unchecked")
	public List<Category> all() {
		return session.createCriteria(Category.class).list();
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public void remove(Category category) {
		long id = category.id;
		List<Subcategory> subs = session.createCriteria(Subcategory.class).add(Restrictions.eq("category_id", id)).list();
		for (Subcategory b : subs) {
			subcategoryDAO.remove(b);
		}
		session.delete(category);
	}
	
	@CommitAfter
	public void add(Category category) {
		session.persist(category);
	}
	
	@CommitAfter
	public boolean contains(Category category) {
		return session.contains(category);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Category> onFrontPage() {
		return session.createCriteria(com.example.tutorial.entities.Category.class).add(Restrictions.eq("onFrontPage", true)).list();
	}

	@CommitAfter
	@SuppressWarnings("unchecked")
	public Category get(String name) {
		Category toReturn = null;
    	try {
			List<Category> tickerInfos = session.createCriteria(com.example.tutorial.entities.Category.class).add(Restrictions.eq("name", name)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
    	return toReturn;
	}


	@CommitAfter
	public void saveOrUpdate(Category category) {
		session.saveOrUpdate(category);
	}
	
	@SuppressWarnings("unchecked")
	@CommitAfter
	public Category getByID(long id) {
		Category toReturn = null;
		List<Category> tickerInfos = session.createCriteria(com.example.tutorial.entities.Category.class).add(Restrictions.eq("id", id)).list();
		if(tickerInfos.size()==1) { 
			toReturn = tickerInfos.get(0);
		}
    	return toReturn;
	}
}
