package com.gil.whatsnew.enums;

public enum ErrorType {

	General_Error("General_Error"),
	Create_Failed("Create_Failed"), 
	Update_Failed("Update_Failed"),
	DaoException("Dao_Exception");
	
	private String generalError;
	
	ErrorType(String generalError) {
		this.generalError = generalError;
	}

	public String getGeneralError() {
		return generalError;
	}
}
