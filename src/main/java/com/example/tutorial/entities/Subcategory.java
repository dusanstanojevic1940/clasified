package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;


@Entity
@Table(name="subcategory")
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="subcategory_id")
	public Long id;
	
	@NonVisual
	@Column(name="category_id")
	public Long category_id;
	
	@Column(name="name")
	public String name;
	
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "subcategory", targetEntity=Post.class, cascade={ CascadeType.ALL})
	//public List<Post> posts; 
	
	@Column(name="shown_on_top_page")
	public boolean showOnTopPage;
	
	//@ManyToOne(targetEntity=Category.class, cascade = { CascadeType.ALL },fetch = FetchType.EAGER)
	//@JoinColumn(name = "category_id",referencedColumnName="category_id")
	//public Category category;
	
	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Subcategory) 
			return ((Subcategory)obj).id.equals(id);
		return false;
	}
}
