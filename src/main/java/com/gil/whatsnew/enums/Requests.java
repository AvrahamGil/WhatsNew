package com.gil.whatsnew.enums;

public enum Requests {

	
	RequestApi("request-api");

	String url;
	
	Requests(String url) {
		this.url= url;
	}
	
	public String getUrl() {
		return this.url;
	}
}
