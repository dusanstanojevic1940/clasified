package com.example.tutorial.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.LikedPostUser;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.PostImage;
import com.example.tutorial.entities.Subcategory;
import com.example.tutorial.entities.TopAdOn;
import com.example.tutorial.util.ImageUtil;

public class PostDAOimpl implements PostDAO {
	@Inject
	private Session session;
	@Inject
	private SettingsDAO settingsDAO;
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Post> all() {
		return (List<Post>)session.createCriteria(Post.class).list();
	}
	
	@CommitAfter
	public void remove(Post post) {
		delete(post.post_id);
	}
	
	@CommitAfter
	public void add(Post post) {
		session.persist(post);
	}
	
	@CommitAfter
	public boolean contains(Post post) {
		return session.contains(post);
	}
	
	@CommitAfter
	public Post getByID(Long id) {
		return (Post)session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", id)).uniqueResult();
	}

	@CommitAfter
	public void update(Post post) {
		session.update(post);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public void delete(long post_id) {
		List<PostImage> zero = session.createCriteria(PostImage.class).add(Restrictions.eq("post_id", post_id)).list();
		for (PostImage b : zero) {
			ImageUtil.deleteImage(settingsDAO, b.imageLocation);
			session.delete(b);
		}
		
		List<LikedPostUser> third = session.createCriteria(LikedPostUser.class).add(Restrictions.eq("post_id", post_id)).list();
		for (LikedPostUser b : third)
			session.delete(b);
		
		List<TopAdOn> fourth = session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		for (TopAdOn b : fourth)
			session.delete(b);
		
		List<FeaturedAdOn> fifth = session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		for (FeaturedAdOn b : fifth)
			session.delete(b);
		
		List<Post> sixth = session.createCriteria(Post.class).add(Restrictions.eq("post_id", post_id)).list();
		for (Post b : sixth)
			session.delete(b);
	}

	public Long getUser(long post_id) {
    	return ((Post)session.createCriteria(Post.class).add(Restrictions.eq("post_id", post_id)).uniqueResult()).user_id;
	}
	

	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<Post> getBySubcategory(long subcategory_id) {
		return (List<Post>)session.createCriteria(Post.class).add(Restrictions.eq("subcategory_id", subcategory_id)).list();		
	}

	@CommitAfter
	public Subcategory getPostSubcategory(long post_id) {
		Post ma = (Post)session.createCriteria(Post.class).add(Restrictions.eq("post_id", post_id)).uniqueResult();
	
		return (Subcategory)session.createCriteria(Subcategory.class).add(Restrictions.eq("id", ma.subcategory_id)).uniqueResult();
	}
	
	@CommitAfter
	public void saveOrUpdate(Post post) {
		session.saveOrUpdate(post);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<String> getImagesForPostID(long id) {
		ArrayList<String> toRet = new ArrayList<String>();
		List<PostImage> pi = session.createCriteria(PostImage.class).add(Restrictions.eq("post_id", id)).list();
		for (PostImage b : pi)
			toRet.add(b.imageLocation);
		return toRet;
	}
	
	@CommitAfter
	public void addImageForPostID(long id, String location) {
		PostImage toSave = new PostImage();
		toSave.post_id = id;
		toSave.imageLocation = location;
		session.persist(toSave);
	}
	
	@SuppressWarnings("unchecked")
	@CommitAfter
	public void deleteImageForPost(long id, String name, String imageURL) {
		List<PostImage> pi = session.createCriteria(PostImage.class).add(Restrictions.eq("post_id", id)).add(Restrictions.eq("imageLocation", name)).list();
		if (pi.size()!=1)
			return;
		File file = new File(imageURL+"/"+name);
		 
		if(file.delete()){
			session.delete(pi.get(0));
		}
	}
	
	@CommitAfter
	public void addTopPostOn(long post_id, Date start, Date end) {
		TopAdOn toSave = new TopAdOn();
		toSave.post_id = post_id;
		toSave.dateStart = start;
		toSave.dateEnd = end;
		
		session.persist(toSave);
	}
	

	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<TopAdOn> getDatesForPostID(long post_id) {
		//loadIfNot();
		List<TopAdOn> t = session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		return t;
		//return t;
	}
	

	@CommitAfter
	@SuppressWarnings("unchecked")
	public Date getLatestDateForTopAd(long post_id) {
		Post po = new Post();
		po.post_id = post_id;
		//List<TopAdOn> t = topAdOn.get(po);
		List<TopAdOn> t = session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		
		List<Date> toReturn = new ArrayList<Date>();
		
		if (t.size()!=0)
			for (TopAdOn p : t)
				toReturn.add(p.dateEnd);
		else 
			return null;
	
		java.util.Collections.sort(toReturn);
		
		return toReturn.get(toReturn.size()-1);
	}

	@CommitAfter
	@SuppressWarnings("unchecked")
	public boolean exists(long id, String name) {
		List<PostImage> pi = session.createCriteria(PostImage.class).add(Restrictions.eq("post_id", id)).list();
		
		for (PostImage i : pi) {
			if (i.imageLocation.substring(0, i.imageLocation.length()-4).equals(name.substring(0, name.length()-4)))
				return true;
		}
		
		return false;
	}
	
	
	
	@CommitAfter
	public void addFeaturedPostOn(long post_id, Date start, Date end) {
		FeaturedAdOn toSave = new FeaturedAdOn();
		toSave.dateStart = start;
		toSave.dateEnd = end;
		toSave.post_id = post_id;
		session.persist(toSave);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public Date getLatestDateForFeaturedAd(long post_id) {
		Post po = new Post();
		po.post_id = post_id;
		//List<FeaturedAdOn> t = featuredAdOn.get(po);
		List<FeaturedAdOn> t = session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		
		List<Date> toReturn = new ArrayList<Date>();
		
		if (t.size()!=0)
			for (FeaturedAdOn p : t)
				toReturn.add(p.dateEnd);
		else 
			return null;
	
		java.util.Collections.sort(toReturn);
		
		return toReturn.get(toReturn.size()-1);
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public List<FeaturedAdOn> getFeaturedOnByPostID(long post_id) {
		Post p = new Post();
		p.post_id = post_id;
		return (List<FeaturedAdOn>)session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
	}
	
	@CommitAfter
	@SuppressWarnings("unchecked")
	public void optimize() {
		List<TopAdOn> taos = session.createCriteria(TopAdOn.class).list();
		List<FeaturedAdOn> faos =session.createCriteria(FeaturedAdOn.class).list();
		Date today = new Date();
		for (TopAdOn t : taos) {
			if (t.dateEnd.before(today)) {
				session.delete(t);
			}
		}
		for (FeaturedAdOn f : faos) {
			if (f.dateEnd.before(today)) {
				session.delete(f);
			}
		}
	}
}
