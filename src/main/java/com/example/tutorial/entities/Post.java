package com.example.tutorial.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;

import javax.persistence.Entity;

@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="post_id")
	public Long post_id;
	
	@Column(name="user_id")
	public Long user_id;
	
	@NonVisual
	@Column(name="subcategory_id")
	public Long subcategory_id;
	
	@Column(length=250)
	public String location;
	
	@Column(length=250)
	public String videoLink;
	
	public PostKind kind;
	
	@Column(length=85)
	public String title;
	
	@Column(length=120000)
	public String text;

	public String ip;
	
	public String phoneForContact;
	
	public String price;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_bid", nullable = false)
    public Date lastBid;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    public Date created;
	
	@PrePersist
    protected void onCreate() {
		lastBid = created = new Date();
    }
	
	@Override
	public int hashCode() {
		return (int)((long)post_id);
	}
	
	@Override
	public boolean equals(Object obj) {
		Post toCmp;
		if (obj instanceof Post)
			toCmp = (Post) obj;
		else return false;
		return post_id.equals(toCmp.post_id);
	}
}
