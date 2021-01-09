package com.gil.whatsnew.enums;

public enum SportType {
	MLB("mlb.com"),
	Syracuse("syracuse.com"),
	DeadSpin("deadspin.com"),
	YardBarker("yardbarker.com"),
	Cbssports("cbssports.com"),
	SportsIllustrated("si.com"),
	ESPN("espn.com"),
	Yahoo("sports.yahoo.com"),
	Fansided("fansided.com"),
	Sbnation("sbnation.com"),
	Sportingnews("sportingnews.com"),

	NewYorkTimes("NewYorkTimes");
	
	private String site;
	
	SportType(String value) {
		this.site = value;
	}
	
	public String getSite() {
		return this.site;
	}

}
