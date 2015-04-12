package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
@Table(name="post_image_table")
public class PostImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="post_image_id")
	public Long post_image_id;
	
	public long post_id;
	public String imageLocation;

	@Override
	public int hashCode() {
		return (int)((long)post_image_id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PostImage) 
			return ((PostImage)obj).post_image_id.equals(post_image_id);
		return false;
	}
}
