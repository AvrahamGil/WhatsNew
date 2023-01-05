package com.gil.whatsnew.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("users")
public class User {

	@Id
	private String userId;
	private String email;
	private String password;
	private String fullName;
	private String country;
	
	public User() {
		
	}
	
	public User(String email, String password, String fullName, String country) {
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.country = country;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() { 
		return userId + "," + email + "," + password + "," + fullName  + "," + country;
	}
}
