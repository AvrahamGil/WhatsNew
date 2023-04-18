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
	private byte[] image;
	private boolean withImage;
	
	public User() {
		
	}
	
	public User(String userId, String email, String password, String fullName, String country, byte[] image,
			boolean withImage) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.country = country;
		this.image = image;
		this.withImage = withImage;
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

	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	public boolean isWithImage() {
		return withImage;
	}

	public void setWithImage(boolean withImage) {
		this.withImage = withImage;
	}

	@Override
	public String toString() { 
		return userId + "," + email + "," + password + "," + fullName  + "," + country + "," + image;
	}
}
