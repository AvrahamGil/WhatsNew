package com.gil.whatsnew.bean;

public class Login {

	private String email;
	private String password;
	private String token;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String userName) {
		this.email = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String userPassword) {
		this.password = userPassword;
	}

	public String getToken() {
		return token;
	}

	public void setCsrfToken(String token) {
		this.token = token;
	}
	
}
