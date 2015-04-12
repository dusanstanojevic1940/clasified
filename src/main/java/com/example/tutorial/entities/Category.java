package com.example.tutorial.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
@Table(name="category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="category_id")
	public Long id;
	
	@Column(name="name")
	public String name;
	
	@Column(name="on_front_page")
	public boolean onFrontPage;
	

	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Category) 
			return ((Category)obj).id.equals(id);
		return false;
	}
}
