package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;
 
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="user_id")
	public Long id;
	
	@Column(name = "username")
	@Validate("required")
	public String username;
	
	@Column(name = "password")
	@Validate("required")
	public String password;
	
	@Column(name = "email")
	@Validate("required")
	public String email;
	
	@Column(name = "phone")
	public String phone;
	
	@Column(name = "coin")
	public int coin;
	
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	//public Set<Post> posts = new HashSet<com.example.tutorial.entities.Post>();
	
	//@ManyToMany (fetch = FetchType.EAGER, mappedBy="liked")
    //public Set<Post> watchlist = new HashSet<Post>();
	
	public boolean isAdmin;
	
	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) 
			return ((User)obj).id.equals(id);
		return false;
	}
	
	@Override
	public String toString() {
		return "Username "+username;
	}
}
