package com.example.tutorial.dao;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.User;

public interface UserDAO {
	@CommitAfter
	public List<User> all();
	
	@CommitAfter
	public void remove(User user);
	
	@CommitAfter
	public boolean addIfNotTaken(User user);
	
	@CommitAfter
	public boolean contains(User user);
	
	@CommitAfter
	public void update(User user);
	
	@CommitAfter
	public User getUserFor(String username, String password);
	
	@CommitAfter
	public User getByID(long id);
	
	@CommitAfter
	public void addToWatchlist(long post_id, long user_id);
	
	@CommitAfter
	public List<Post> getMyAdsPosts(long user_id);
	
	@CommitAfter
	public void removeFromWatchlist(long user_id, long post_id);
	
	@CommitAfter
	public List<Post> getMyWatchlistPosts(long user_id);
	
	@CommitAfter
	public void saveOrUpdate(User user);
}
