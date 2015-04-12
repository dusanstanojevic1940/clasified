package com.example.tutorial.entities;

public enum PostKind {
	OFERING("offering"), WANTED("wanted");
	
	private String name;
	
	PostKind(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
