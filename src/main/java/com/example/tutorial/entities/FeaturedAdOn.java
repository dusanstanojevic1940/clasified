package com.example.tutorial.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class FeaturedAdOn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="id")
	public Long id;
	
	public long post_id;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateStart", nullable = false)
	public Date dateStart;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateEnd", nullable = false)
	public Date dateEnd;

	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FeaturedAdOn) 
			return ((FeaturedAdOn)obj).id.equals(id);
		return false;
	}
}
