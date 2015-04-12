package com.example.tutorial.dao;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.example.tutorial.entities.PayPalAccount;
import com.example.tutorial.entities.Settings;

public interface SettingsDAO {
	@CommitAfter
	public Settings getSettings();
	
	@CommitAfter
	public void update(Settings toSave);
	
	@CommitAfter
	public PayPalAccount getPayPalAccount();
	
	@CommitAfter
	public void updatePayPalAccount(PayPalAccount ppAcc);
}
