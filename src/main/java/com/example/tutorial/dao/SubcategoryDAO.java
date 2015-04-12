package com.example.tutorial.dao;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.Subcategory;

public interface SubcategoryDAO {
	public List<Subcategory> all();
	
	@CommitAfter
	public void remove(Subcategory subcategory);
	
	@CommitAfter
	public void add(Subcategory subcategory);
	
	@CommitAfter
	public boolean contains(Subcategory subcategory);
	
	@CommitAfter
	public Subcategory getByID(Long id);
	
	@CommitAfter
	public void update(Subcategory sub);
	
	@CommitAfter
	public List<Subcategory> getByCategory(long id);
}
