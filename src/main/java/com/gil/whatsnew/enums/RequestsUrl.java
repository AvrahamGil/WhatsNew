package com.gil.whatsnew.enums;

public enum RequestsUrl {

	NewsDataAPI("https://newsdata.io/api/1/latest?language=en&category=","apiKey","language","category",
			"key","en"),
	GNewsAPI("https://gnews.io/api/v4/top-headlines?language=en&category=","apikey","language","category",
			"key","en"),
	CurrentsApi("https://api.currentsapi.services/v1/latest-news?language=en","apiKey","language","",
			"key","en"),
	Captcha("https://www.google.com/recaptcha/api/siteverify" , "null",
			"null","null","null","null"),
	BOTH("" , "null", "null","null","null","null"),
	DIFF("" , "null", "null","null","null","null");

	final String domain;
	final String apiKeyParameter;
	final String languageParameter;
	final String categoryParameter;
	final String apiKeyValue;
	final String languageValue;
	
	RequestsUrl(String domain, String apiKeyParameter, String languageParameter, String categoryParameter, String apiKeyValue, String languageValue) {
		this.domain = domain;
		this.apiKeyParameter = apiKeyParameter;
		this.languageParameter = languageParameter;
		this.categoryParameter = categoryParameter;
		this.apiKeyValue = apiKeyValue;
		this.languageValue = languageValue;
	}

	public String getDomain() {
		return domain;
	}

	public String getApiKeyParameter() {
		return apiKeyParameter;
	}

	public String getLanguageParameter() {
		return languageParameter;
	}

	public String getCategoryParameter() {
		return categoryParameter;
	}

	public String getApiKeyValue() {
		return apiKeyValue;
	}

	public String getLanguageValue() {
		return languageValue;
	}
}
