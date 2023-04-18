package com.gil.whatsnew.bean;

import org.springframework.stereotype.Component;

@Component
public class ErrorLog {

	private String message;
	private int statusCode;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
