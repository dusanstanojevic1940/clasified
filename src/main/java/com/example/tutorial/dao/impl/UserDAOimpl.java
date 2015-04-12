package com.example.tutorial.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.LikedPostUser;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.User;

public class UserDAOimpl implements UserDAO {
	@Inject
	private Session session;
	
	@SuppressWarnings("unchecked")
	@CommitAfter
	public List<User> all() {
		return session.createCriteria(User.class).list();
	}
	
	@CommitAfter
	public void remove(User user) {
		session.delete(user);
	}
	
	@CommitAfter
	public boolean addIfNotTaken(User user) {
		boolean toReturn;
    	@SuppressWarnings("rawtypes")
		List tickerInfos = session.createCriteria(com.example.tutorial.entities.User.class).add(Restrictions.eq("username", user.username)).list();
		if(tickerInfos.size()<1) { 
	         session.persist(user);
	         toReturn = true;
		}
		else 
			toReturn = false;

		return toReturn;
	}
	
	@CommitAfter
	public boolean contains(User user) {
		return session.contains(user);
	}
	
	@CommitAfter
	public void update(User user) {
		session.update(user);
	}

	@CommitAfter
	public User getUserFor(String username, String password) {
		User toReturn = null;
    	try {
 
    		@SuppressWarnings("unchecked")
			List<User> tickerInfos = session.createCriteria(com.example.tutorial.entities.User.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
    	return toReturn;
	}
	
	@CommitAfter
	public User getByID(long id) {
		User toReturn = null;
    	try {
 
    		@SuppressWarnings("unchecked")
			List<User> tickerInfos = session.createCriteria(com.example.tutorial.entities.User.class).add(Restrictions.eq("id", id)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
    	return toReturn;
	}
	
	public void addToWatchlist(long post_id, long user_id) {
		LikedPostUser toSave = new LikedPostUser();
		toSave.post_id = post_id;
		toSave.user_id = user_id;
		session.save(toSave);
	}

	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Post> getMyAdsPosts(long user_id) {
		return session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("user_id", user_id)).list();
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public void removeFromWatchlist(long user_id, long post_id) {
		List<LikedPostUser> list = session.createCriteria(LikedPostUser.class).add(Restrictions.eq("post_id", post_id)).add(Restrictions.eq("user_id", user_id)).list();
		for (LikedPostUser toDel : list)
			session.delete(toDel);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Post> getMyWatchlistPosts(long user_id) {
		ArrayList<Post> toRet = new ArrayList<Post>();
		
		List<LikedPostUser> ma = session.createCriteria(LikedPostUser.class).add(Restrictions.eq("user_id", user_id)).list();
		for (LikedPostUser b : ma) {
			List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", b.post_id)).list();
    		if(tickerInfos.size()==1) { 
    			toRet.add(tickerInfos.get(0));
    		}
		}
		return toRet;
	}
	

	@CommitAfter
	public void saveOrUpdate(User user) {
		session.saveOrUpdate(user);
	}
}
