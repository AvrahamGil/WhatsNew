package com.gil.whatsnew.enums;

public enum ErrorType {

	General_Error("General error, please check again later."),
	Initialize_Error("Initialize articles failed"),
	Maximum_Size("File is larger than expected."),
	Create_Failed("Create failed, please check your details and try again."), 
	Update_Failed("Update Failed, please make sure you are logging in, and try again"),
	Api_Failed("Get api response failed, check your api key again"),
	Get_List_Failed("Get list failed, please check your details and try again"),
	DaoException("Dao Exception, check object details or connections."),
	Email_Already_Exist("Email already exist, please check your email"),
	User_Already_Exist("User already exist, please check your details"),
	User_Details("Get user detail failed, check if you are logged in"),
	User_Not_Exist("No such email or password, please sign up and login."),
	Create_User_Failed("Please check your details and try again"),
	Login_Failed("One or more details are incorrect"),
	Read_Json_Failed("Cannot read json file, please check file path and try again"),
	Write_Json_Failed("Cannot write to json file, please check file path and try again"),
	Domains_Failed("Domains failed, check domains correctly or file path"),
	Article_Already_Liked("Article already liked by the user");
	
	private String message;
	
	ErrorType(String generalError) {
		this.message = generalError;
	}

	public String getMessage() {
		return message;
	}
}
