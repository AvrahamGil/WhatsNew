package com.gil.whatsnew.enums;

public enum NewsType {
	
	//Context Api
	Theblaze("theblaze.com"),
	Time("time.com"),
	WallStreetJournal("wsj.com"),
	CNN("cnn.com"),
	Ynetnews("ynetnews.com"),
	Theguardian("theguardian.com"),
	ABCnews("abcnews.go.com"),
	BBCnews("BBC"),
	JerusalemNews("N12"),
	Covid19("covid-19"),
	NewYorkTimes("NewYorkTimes"),
	NBC("nbc.com");

			  
	private String site;
	
	NewsType(String value) {
		this.site = value;
	}
	
	public String getSite() {
		return this.site;
	}
}
