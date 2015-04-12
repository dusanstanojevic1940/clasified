package com.example.tutorial.dao.impl;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.entities.PayPalAccount;
import com.example.tutorial.entities.Settings;

public class SettingsDAOimpl implements SettingsDAO {
	@Inject
	private Session session;
	
	@CommitAfter
	public Settings getSettings() {
		if (session.createCriteria(Settings.class).list().size()==0)
			session.save(new Settings());
		
		return (Settings)session.createCriteria(Settings.class).list().get(0);
	}
	
	@CommitAfter
	public void update(Settings toUpdate) {
		session.update(toUpdate);
	}
	
	@CommitAfter
	public PayPalAccount getPayPalAccount() {
		if (session.createCriteria(PayPalAccount.class).list().size()==0)
			session.save(new PayPalAccount());
		
		return (PayPalAccount)session.createCriteria(PayPalAccount.class).list().get(0);
	}
	
	@CommitAfter
	public void updatePayPalAccount(PayPalAccount ppAcc) {
		session.update(ppAcc);
	}
}
