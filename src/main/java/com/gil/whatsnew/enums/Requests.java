package com.gil.whatsnew.enums;

public enum Requests {

	RapidHost("x-rapidapi-host"),
	RapidKey("x-rapidapi-key"),
	NewYorkApi("https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=K3uyNsrevkFrR487I5sN5qg4GASOU4iz&q="),
	RapidApi("https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/search/NewsSearchAPI?toPublishedDate=null&fromPublishedDate=null&withThumbnails=true&pageSize=15&autoCorrect=false&pageNumber=1&q="),
	RapidKeyValue("57a5f5629bmsh4e8fafc07e244d8p1a6da0jsn0b7d9a7cd8cf"),
	
	ContextRapidHostValue("contextualwebsearch-websearch-v1.p.rapidapi.com");
	
	String strings;
	
	Requests(String url) {
		this.strings= url;
	}
	
	public String getValue() {
		return this.strings;
	}
}
