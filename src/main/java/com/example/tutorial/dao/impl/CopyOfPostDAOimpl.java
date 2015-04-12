package com.example.tutorial.dao.impl;

public class CopyOfPostDAOimpl {}//implements PostDAO {
	/*@Inject
	private Session session;
	
	private volatile Map<Subcategory,List<Post>> postsPerCategory = new HashMap<Subcategory, List<Post>>();
	private volatile Map<Post, List<TopAdOn>> topAdOn = new HashMap<Post, List<TopAdOn>>();
	private volatile Map<Post, List<FeaturedAdOn>> featuredAdOn = new HashMap<Post, List<FeaturedAdOn>>();
	private volatile List<Post> posts = new ArrayList<Post>();
	private volatile boolean loaded = false;
	
	private void loadIfNot() {
		if (!loaded) {
			loadNoCommit();
			loaded = true;
		}
	}
	
	private void lazyReloadLoad() {
		new Thread(new Runnable() {
			@CommitAfter
			public void run() {
		
		List<Subcategory> subs = session.createCriteria(Subcategory.class).list();
		for (Subcategory sub : subs) {
			ArrayList<Post> posts = new ArrayList<Post>();
			
			List<BelongsPostSubcategory> ma = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("subcategory_id", sub.id)).list();
			for (BelongsPostSubcategory b : ma) {
				List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", b.post_id)).list();
	    		if(tickerInfos.size()==1) { 
	    			posts.add(tickerInfos.get(0));
	    		}
			}
			postsPerCategory.put(sub, posts);
		}
		
		posts = session.createCriteria(Post.class).list();
		
		for (Post p : posts)
			topAdOn.put(p, session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
		
		for (Post p : posts)
			featuredAdOn.put(p, session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
			
			}}).start();
	}
	
	@CommitAfter
	public void load() {
		List<Subcategory> subs = session.createCriteria(Subcategory.class).list();
		for (Subcategory sub : subs) {
			ArrayList<Post> posts = new ArrayList<Post>();
			
			List<BelongsPostSubcategory> ma = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("subcategory_id", sub.id)).list();
			for (BelongsPostSubcategory b : ma) {
				List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", b.post_id)).list();
	    		if(tickerInfos.size()==1) { 
	    			posts.add(tickerInfos.get(0));
	    		}
			}
			postsPerCategory.put(sub, posts);
		}
		
		posts = session.createCriteria(Post.class).list();
		
		for (Post p : posts)
			topAdOn.put(p, session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
		
		for (Post p : posts)
			featuredAdOn.put(p, session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
	}
	
	public void loadNoCommit() {
		List<Subcategory> subs = session.createCriteria(Subcategory.class).list();
		for (Subcategory sub : subs) {
			ArrayList<Post> posts = new ArrayList<Post>();
			
			List<BelongsPostSubcategory> ma = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("subcategory_id", sub.id)).list();
			for (BelongsPostSubcategory b : ma) {
				List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", b.post_id)).list();
	    		if(tickerInfos.size()==1) { 
	    			posts.add(tickerInfos.get(0));
	    		}
			}
			postsPerCategory.put(sub, posts);
		}
		
		posts = session.createCriteria(Post.class).list();
		
		for (Post p : posts)
			topAdOn.put(p, session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
		
		for (Post p : posts)
			featuredAdOn.put(p, session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", p.post_id)).list());
			
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> all() {
		loadIfNot();
		return posts;
	}
	
	@CommitAfter
	public void remove(Post post) {
		long post_id = post.post_id;
		
		List<BelongsPostSubcategory> first = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("post_id", post_id)).list();
		for (BelongsPostSubcategory b : first)
			session.delete(b);
		
		List<CreatedPostUser> second = session.createCriteria(CreatedPostUser.class).add(Restrictions.eq("post_id", post_id)).list();
		for (CreatedPostUser b : second)
			session.delete(b);
		
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
		
		lazyReloadLoad();
	}
	
	@CommitAfter
	public void add(Post post) {
		session.persist(post);
		lazyReloadLoad();
	}
	
	@CommitAfter
	public boolean contains(Post post) {
		loadIfNot();
		return posts.contains(post);//session.contains(post);
	}
	
	@CommitAfter
	public Post getByID(Long id) {
		loadIfNot();
		Post toReturn = null;
    	try {
 
    		@SuppressWarnings("rawtypes")
			List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", id)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
    	return toReturn;
	}

	@CommitAfter
	public void update(Post post) {
		session.update(post);
		lazyReloadLoad();
	}
	
	@CommitAfter
	public void delete(long post_id) {
		List<BelongsPostSubcategory> first = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("post_id", post_id)).list();
		for (BelongsPostSubcategory b : first)
			session.delete(b);
		
		List<CreatedPostUser> second = session.createCriteria(CreatedPostUser.class).add(Restrictions.eq("post_id", post_id)).list();
		for (CreatedPostUser b : second)
			session.delete(b);
		
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
		
		lazyReloadLoad();
	}

	public Long getUser(long post_id) {
		loadIfNot();
		CreatedPostUser toReturn = null;
    	try {
    		@SuppressWarnings("rawtypes")
			List<CreatedPostUser> tickerInfos = session.createCriteria(com.example.tutorial.entities.CreatedPostUser.class).add(Restrictions.eq("post_id", post_id)).list();
    		if(tickerInfos.size()==1) { 
    			toReturn = tickerInfos.get(0);
    		}
 
    	} catch(RuntimeException e) {
    		toReturn = null;
    	} 
		return toReturn.user_id;
	}
	

	@CommitAfter
	public List<Post> getBySubcategory(long subcategory_id) {
		/*ArrayList<Post> toRet = new ArrayList<Post>();
		
		List<BelongsPostSubcategory> ma = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("subcategory_id", subcategory_id)).list();
		for (BelongsPostSubcategory b : ma) {
			List<Post> tickerInfos = session.createCriteria(com.example.tutorial.entities.Post.class).add(Restrictions.eq("post_id", b.post_id)).list();
    		if(tickerInfos.size()==1) { 
    			toRet.add(tickerInfos.get(0));
    		}
		}
		loadIfNot();
		Subcategory s = new Subcategory();
		s.id = subcategory_id;
		return postsPerCategory.get(s);
	}
	

	@CommitAfter
	public void addToSubcategory(long post_id, long subcategory_id) {
		BelongsPostSubcategory toSave = new BelongsPostSubcategory();
		toSave.post_id = post_id;
		toSave.subcategory_id = subcategory_id;
		session.persist(toSave);
		lazyReloadLoad();
	}
	

	@CommitAfter
	public void addToUser(long post_id, long user_id) {
		CreatedPostUser toSave = new CreatedPostUser();
		toSave.post_id = post_id;
		toSave.user_id = user_id;
		session.persist(toSave);
		lazyReloadLoad();
	}
	
	@CommitAfter
	public Subcategory getPostSubcategory(long post_id) {
		loadIfNot();
		for (Map.Entry<Subcategory, List<Post>> ppc : postsPerCategory.entrySet())
			for (Post p : ppc.getValue())
				if (p.post_id.equals(post_id))
					return ppc.getKey();
		/*
		List<BelongsPostSubcategory> ma = session.createCriteria(BelongsPostSubcategory.class).add(Restrictions.eq("post_id", post_id)).list();
		if (ma.size()==1) {
			List<Subcategory> b = session.createCriteria(Subcategory.class).add(Restrictions.eq("id", ma.get(0).subcategory_id)).list();
			if  (b.size()==1)
				return b.get(0);
		}
		return null;
	}
	
	@CommitAfter
	public void saveOrUpdate(Post post) {
		session.saveOrUpdate(post);
		lazyReloadLoad();
	}
	
	@CommitAfter
	public List<String> getImagesForPostID(long id) {
		loadIfNot();
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
		lazyReloadLoad();
	}
	
	@CommitAfter
	public void deleteImageForPost(long id, String name, String imageURL) {
		List<PostImage> pi = session.createCriteria(PostImage.class).add(Restrictions.eq("post_id", id)).add(Restrictions.eq("imageLocation", name)).list();
		if (pi.size()!=1)
			return;
		File file = new File(imageURL+"/"+name);
		 
		if(file.delete()){
			session.delete(pi.get(0));
		}
		lazyReloadLoad();
	}
	
	@CommitAfter
	public void addTopPostOn(long post_id, Date start, Date end) {
		TopAdOn toSave = new TopAdOn();
		toSave.post_id = post_id;
		toSave.dateStart = start;
		toSave.dateEnd = end;
		
		session.persist(toSave);
		lazyReloadLoad();
	}
	

	@CommitAfter
	public List<TopAdOn> getDatesForPostID(long post_id) {
		loadIfNot();
		//List<TopAdOn> t = session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		Post p = new Post();
		p.post_id = post_id;
		return topAdOn.get(p);
		//return t;
	}
	

	@CommitAfter
	public Date getLatestDateForTopAd(long post_id) {
		loadIfNot();
		Post po = new Post();
		po.post_id = post_id;
		List<TopAdOn> t = topAdOn.get(po);
		//List<TopAdOn> t = session.createCriteria(TopAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		
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
	public boolean exists(long id, String name) {
		loadIfNot();
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
		lazyReloadLoad();
	}
	
	@CommitAfter
	public Date getLatestDateForFeaturedAd(long post_id) {
		loadIfNot();
		Post po = new Post();
		po.post_id = post_id;
		List<FeaturedAdOn> t = featuredAdOn.get(po);
		//List<FeaturedAdOn> t = session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		
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
	public List<FeaturedAdOn> getFeaturedOnByPostID(long post_id) {
		loadIfNot();
		//List<FeaturedAdOn> t = session.createCriteria(FeaturedAdOn.class).add(Restrictions.eq("post_id", post_id)).list();
		Post p = new Post();
		p.post_id = post_id;
		return featuredAdOn.get(p);
		//return t;
	}
	
	@CommitAfter
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
		
		
		
		lazyReloadLoad();
	}
}*/
