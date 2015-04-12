package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class SellingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="transaction_id")
	public Long id;
	
	public String itemName;
	public String itemLabel;
	public String itemPrice;
	public String currencyCode;
	public int position;
	public int coins;

	@Override
	public String toString() {
		return itemLabel;
	}
	
	@Override
	public int hashCode() {
		return (int)((long)id);
	};
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SellingItem) 
			return ((SellingItem)obj).id.equals(id);
		return false;
	}
}
