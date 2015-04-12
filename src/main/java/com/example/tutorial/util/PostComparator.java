package com.example.tutorial.util;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.example.tutorial.dao.PostDAO;
import com.example.tutorial.entities.FeaturedAdOn;
import com.example.tutorial.entities.Post;
import com.example.tutorial.entities.TopAdOn;

public class PostComparator implements Comparator<com.example.tutorial.entities.Post> {
	private PostDAO postDAO;
	
	public PostComparator(PostDAO postDAO) {
		this.postDAO = postDAO;
	}
	
	public int compare(Post f, Post s) {
		if ((getIsFeaturedAdToday(f.post_id)||getIsTopAdToday(f.post_id)) && (getIsFeaturedAdToday(s.post_id) || getIsTopAdToday(s.post_id))) {
			return s.lastBid.compareTo(f.lastBid);
		}
		if (getIsFeaturedAdToday(f.post_id) || getIsTopAdToday(f.post_id))
			return -1;
		if (getIsFeaturedAdToday(s.post_id) || getIsTopAdToday(s.post_id))
			return 1;
		return s.lastBid.compareTo(f.lastBid);
	}
	
	public boolean getIsTopAdToday(long id) {
		List<TopAdOn> dates = postDAO.getDatesForPostID(id);
		
		Date today = new Date();
		
		for (TopAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
	
	public boolean getIsFeaturedAdToday(long id) {
		List<FeaturedAdOn> dates = postDAO.getFeaturedOnByPostID(id);
		
		Date today = new Date();
		
		for (FeaturedAdOn date : dates) {
			if (today.compareTo(date.dateStart)==1 && today.compareTo(date.dateEnd)==-1)
				return true;
		}
		return false;
	}
}
