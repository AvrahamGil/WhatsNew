package com.gil.whatsnew.enums;

public enum BusinessType {

	//Context Api
	CNBC("cnbc.com"),
	FT("ft.com"),
	Forbes("forbes.com"),
	Fool("fool.com"),
	Bloomberg("bloomberg.com"),
	CleanTechnica("cleantechnica.com"),
	IBtimes("ibtimes.com"),
	MarketWatch("marketwatch.com"),
	Entrepreneur("entrepreneur.com"),
	YahooFinance("finance.yahoo.com"),
	BusinessInsider("businessinsider.com"),
	Zacks("zacks.com");

	  
	private String site;
	
	BusinessType(String value) {
		this.site = value;
	}
	
	public String getSite() {
		return this.site;
	}
}
