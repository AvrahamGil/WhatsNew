package com.gil.whatsnew.enums;

public enum Requests {

	RapidHost("x-rapidapi-host"),
	RapidKey("x-rapidapi-key"),
	NewYorkApi("NewYorkApi"),
	RapidApi("RapidApi"),
	RapidKeyValue("RapidKeyValue"),
	Captcha("https://www.google.com/recaptcha/api/siteverify"),
	ContextRapidHostValue("ContextRapidHostValue");
	
	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
