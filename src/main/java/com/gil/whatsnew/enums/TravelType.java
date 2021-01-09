package com.gil.whatsnew.enums;

public enum TravelType {
	
	Traveldailynews("traveldailynews.com"),
	Travelandtourworld("travelandtourworld.com"),
	Skift("skift.com"),
	Phocuswire("phocuswire.com"),
	Travelpulse("travelpulse.com"),
	Ttgmedia("ttgmedia.com"),
	Ttnworldwide("ttnworldwide.com"),
	Eturbonews("eturbonews.com"),
	Visitseattle("visitseattle.org"),
	
	TravelWeekly("travelweekly.com"),
	
	Businesstravelnews("businesstravelnews.com"),
	LonelyPlanet("lonelyplanet.com");

	private String site;
	
	TravelType(String value) {
		this.site = value;
	}
	
	public String getSite() {
		return this.site;
	}
}
