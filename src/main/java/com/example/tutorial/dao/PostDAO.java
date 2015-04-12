package com.example.tutorial.dao;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.Subcategory;
import com.example.tutorial.entities.TopAdOn;

public interface PostDAO {
	public List<Post> all();
	
	@CommitAfter
	public void remove(Post post);
	
	@CommitAfter
	public void add(Post post);
	
	@CommitAfter
	public void saveOrUpdate(Post post);
	
	@CommitAfter
	public boolean contains(Post post);
	
	@CommitAfter
	public Post getByID(Long id);
	
	@CommitAfter
	public void update(Post post);
	
	@CommitAfter
	public void delete(long id);
	
	@CommitAfter
	public Long getUser(long post_id);
	
	@CommitAfter
	public List<Post> getBySubcategory(long subcategory_id);
	
	@CommitAfter
	public Subcategory getPostSubcategory(long post_id);
	
	@CommitAfter
	public void addImageForPostID(long id, String location);
	
	@CommitAfter
	public List<String> getImagesForPostID(long id);

	@CommitAfter
	public void deleteImageForPost(long id, String name, String imageURL);
	
	@CommitAfter
	public void addTopPostOn(long post_id, Date start, Date end);
	
	@CommitAfter
	public List<TopAdOn> getDatesForPostID(long post_id);
	
	@CommitAfter
	public Date getLatestDateForTopAd(long post_id);
	
	@CommitAfter
	public boolean exists(long id, String name);
	
	@CommitAfter
	public void addFeaturedPostOn(long post_id, Date start, Date end);
	
	@CommitAfter
	public Date getLatestDateForFeaturedAd(long post_id);
	
	@CommitAfter
	public List<FeaturedAdOn> getFeaturedOnByPostID(long post_id);
	
	@CommitAfter
	public void optimize();
}
