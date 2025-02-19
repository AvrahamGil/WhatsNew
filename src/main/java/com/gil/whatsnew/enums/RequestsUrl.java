package com.gil.whatsnew.enums;

public enum RequestsUrl {

	NewsDataAPI("https://newsdata.io/api/1/latest?language=en&category=","apiKey","language","category",
			"pub_637969fd831ca2d52f4c8f769d3ecf94330a7","en"),
	GNewsAPI("https://gnews.io/api/v4/top-headlines?language=en&category=","apikey","language","category",
			"51aab3ce7df5ca5882ae15425bd71acd","en"),
	CurrentsApi("https://api.currentsapi.services/v1/latest-news?language=en","apiKey","language","",
			"m2eJRAat8L8gNdRdWWjz5kv_w8bwJMsUJt3rkuwfVVtVO_a5","en"),
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
