package com.example.tutorial.dao;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.SellingItem;

public interface SellingItemDAO {
	@CommitAfter
	public List<SellingItem> all();
	
	@CommitAfter
	public SellingItem get(long id);
	
	@CommitAfter
	public void delete(long id);
	
	@CommitAfter
	public void save(SellingItem toSave);

	@CommitAfter
	public void delete(SellingItem toDel);
	
	@CommitAfter
	public void saveOrUpdate(SellingItem toSave);
}
