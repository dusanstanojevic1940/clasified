package com.example.tutorial.services;

import com.example.tutorial.entities.User;

public interface Authenticator {
	User getLoggedUser();
	boolean isLoggedIn();
	void login(String username, String password) throws com.example.tutorial.security.AuthenticationException;
    void logout();
}
