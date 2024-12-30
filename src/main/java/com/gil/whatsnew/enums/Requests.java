package com.gil.whatsnew.enums;

public enum Requests {

	CurrentsKeyParameter("apiKey"),
	LanguageParameter("language"),
	CategoryParameter("category"),

	CurrentsAPIKeyValue("m2eJRAat8L8gNdRdWWjz5kv_w8bwJMsUJt3rkuwfVVtVO_a5"),
	LanguageValue("en"),
	CurrentsApi("https://api.currentsapi.services/v1/search?language=en&category="),
	Captcha("https://www.google.com/recaptcha/api/siteverify");

	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
