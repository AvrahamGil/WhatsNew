package com.gil.whatsnew.bean;

import org.springframework.stereotype.Component;

@Component
public class CusomErrorResponse {

	private int status;
	private String errorMessage;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String error) {
		this.errorMessage = error;
	}
}
