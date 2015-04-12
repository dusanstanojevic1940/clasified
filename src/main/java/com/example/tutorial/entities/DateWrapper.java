package com.example.tutorial.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
@Table(name = "date_wrapper")
public class DateWrapper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="ddata_id")
	public Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date", nullable=false)
	public Date date;
	
	//@ManyToMany
	//public Set<Post> posts = new HashSet<Post>();
	
	@Override
	public int hashCode() {
		return (int)((long)id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DateWrapper)
			return ((DateWrapper)obj).id.equals(id);
		return false;
	}
}
