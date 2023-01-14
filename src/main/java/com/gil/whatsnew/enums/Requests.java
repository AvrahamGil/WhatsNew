package com.gil.whatsnew.enums;

public enum Requests {

	RapidHost("x-rapidapi-host"),
	RapidKey("x-rapidapi-key"),
	NewYorkApi("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=bPgxNsAMl6CWyxE5Ujt0DgmHVR4XowmX&q="),
	RapidApi("https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/search/NewsSearchAPI?toPublishedDate=null&fromPublishedDate=null&withThumbnails=true&pageSize=15&autoCorrect=false&pageNumber=1&q="),
	RapidKeyValue("c8e4e21d5cmsh0da2d214f3b6c2bp1a5e2ejsn75621a071d73"),
	
	ContextRapidHostValue("contextualwebsearch-websearch-v1.p.rapidapi.com");
	
	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
