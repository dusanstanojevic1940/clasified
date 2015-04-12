package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class LikedPostUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="id")
	public Long id;
	
	public long post_id;
	public long user_id;
	
	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LikedPostUser) 
			return ((LikedPostUser)obj).id.equals(id);
		return false;
	}
}
