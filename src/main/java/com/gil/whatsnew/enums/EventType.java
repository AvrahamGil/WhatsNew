package com.gil.whatsnew.enums;

public enum EventType {

	Add("ADD"),
	Delete("DELETE"),
	Get("GET");
	
	private String type;
	
	EventType(String type) {
		this.type = type;
	}
	
	public String getEventType() {
		return type;
	}
	
}
