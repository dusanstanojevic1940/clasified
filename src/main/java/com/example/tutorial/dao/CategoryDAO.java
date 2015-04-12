package com.example.tutorial.dao;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.Category;

public interface CategoryDAO {
	public List<Category> all();
	
	@CommitAfter
	public void remove(Category category);
		
	@CommitAfter
	public void saveOrUpdate(Category category);
	
	@CommitAfter
	public void add(Category category);
	
	@CommitAfter
	public boolean contains(Category category);

	@CommitAfter
	public Category get(String name);
	
	@CommitAfter
	public List<Category> onFrontPage();
	
	@CommitAfter
	public Category getByID(long id);
}
