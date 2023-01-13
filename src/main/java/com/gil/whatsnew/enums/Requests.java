package com.gil.whatsnew.enums;

public enum Requests {

	RapidHost("x-rapidapi-host"),
	RapidKey("x-rapidapi-key"),
	NewYorkApi("NewYorkApi"),
	RapidApi("RapidApi"),
	RapidKeyValue("RapidKeyValue"),
	
	ContextRapidHostValue("ContextRapidHostValue");
	
	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
