package com.gil.whatsnew.enums;

public enum TechnologyType {

	Sciencedaily("sciencedaily.com"),
	Gizmodo("gizmodo.com"),
	Engadget("engadget.com"),
	Theverge("theverge.com"),
	Axios("axios.com"),
	Wired("wired.com"),
	Arstechnica("arstechnica.com"),
	Thenextweb("thenextweb.com"),
	Techcrunch("techcrunch.com"),
	
	Bitcoin("bitcoin"),
	
	Apple("apple"),
	TechRadar("techradar");

	private String site;
	
	TechnologyType(String value) {
		this.site = value;
	}
	
	public String getSite() {
		return this.site;
	}
}
