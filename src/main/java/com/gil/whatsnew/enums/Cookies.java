package com.gil.whatsnew.enums;

public enum Cookies {

	XCSRFTOKEN("X-CSRF-TOKEN"),
	XTOKEN("X-TOKEN"),
	RECAPTCHA("RECAPTCHA");
	
	private String name;
	
	Cookies(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
