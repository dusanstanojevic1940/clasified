package com.example.tutorial.services;

import java.io.InputStream;

public interface CleanUpAndSaveService {
	public void execute();
	public void optimize();
	public InputStream save();
}
