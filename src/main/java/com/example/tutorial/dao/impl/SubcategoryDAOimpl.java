package com.example.tutorial.dao.impl;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SubcategoryDAO;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.Subcategory;

public class SubcategoryDAOimpl implements SubcategoryDAO {
	@Inject
	private Session session;
	
	@Inject
	private PostDAO postDAO;
	
	@SuppressWarnings("unchecked")
	public List<Subcategory> all() {
		return session.createCriteria(Subcategory.class).list();
	}
	
	@SuppressWarnings("unchecked")
	@CommitAfter
	public void remove(Subcategory subcategory) {
		long subcategory_id = subcategory.id;
		
		List<Post> posts = session.createCriteria(Post.class).add(Restrictions.eq("subcategory_id", subcategory_id)).list();
		for (Post post : posts) {
			postDAO.remove(post);
		}
			
		session.delete(subcategory);
	}
	
	@CommitAfter
	public void add(Subcategory subcategory) {
		session.persist(subcategory);
	}
	
	@CommitAfter
	public boolean contains(Subcategory subcategory) {
		return session.contains(subcategory);
	}

	@CommitAfter
	@SuppressWarnings("unchecked")
	public Subcategory getByID(Long id) {
		Subcategory toReturn = null;
    	try {
 
			List<Subcategory> tickerInfos = session.createCriteria(com.example.tutorial.entities.Subcategory.class).add(Restrictions.eq("id", id)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
    	return toReturn;
	}
	

	@CommitAfter
	public void update(Subcategory sub) {
		session.update(sub);
	}

	@SuppressWarnings("unchecked")
	@CommitAfter
	public List<Subcategory> getByCategory(long id) {
		
		List<Subcategory> tickerInfos = session.createCriteria(com.example.tutorial.entities.Subcategory.class).add(Restrictions.eq("category_id", id)).list();
    	
		return tickerInfos;
	}
}
