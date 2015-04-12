package com.example.tutorial.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="transaction_id")
	public Long id;
	
	public long user_id;
	public String amountPayed;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	public Date created;
	
	public int coins;
	
	public boolean IPN;
	
	public boolean completed;
	
	public String token;

	@PrePersist
    protected void onCreate() {
		created = new Date();
    }

	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Transaction) 
			return ((Transaction)obj).id.equals(id);
		return false;
	}
}
