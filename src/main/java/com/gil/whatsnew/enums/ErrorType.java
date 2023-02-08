package com.gil.whatsnew.enums;

public enum ErrorType {

	General_Error("General error, please check again later."),
	Initialize_Error("Initialize articles failed"),
	Create_Failed("Create failed, please check your details and try again."), 
	Update_Failed("Update Failed, please make sure you are logging in, and try again"),
	Api_Failed("Get api response failed, check your api key again"),
	Get_List_Failed("Get list failed, please check your details and try again"),
	DaoException("Dao Exception, check object details or connections."),
	Email_Already_Exist("Email already exist, please check your email"),
	User_Already_Exist("User already exist, please check your details"),
	User_Details("Get user detail failed, check if you are logged in"),
	Create_User_Failed("Please check your details and try again"),
	Read_Json_Failed("Cannot read json file, please check file path and try again"),
	Domains_Failed("Domains failed, check domains correctly or file path");
	
	private String message;
	
	ErrorType(String generalError) {
		this.message = generalError;
	}

	public String getMessage() {
		return message;
	}
}
