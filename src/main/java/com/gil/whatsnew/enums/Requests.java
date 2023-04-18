package com.gil.whatsnew.enums;

public enum Requests {

	RapidHost("x-rapidapi-host"),
	RapidKey("x-rapidapi-key"),
	RapidApi("https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/search/NewsSearchAPI?toPublishedDate=null&fromPublishedDate=null&withThumbnails=true&pageSize=15&autoCorrect=false&pageNumber=1&q="),
	RapidKeyValue("{Your-Rapid-API-Key}"),
	Captcha("https://www.google.com/recaptcha/api/siteverify"),
	ContextRapidHostValue("contextualwebsearch-websearch-v1.p.rapidapi.com");
	
	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
