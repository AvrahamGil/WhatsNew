package com.gil.whatsnew.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("contact_me")
public class ContactMe {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String country;
	private String message;
	
	
	public ContactMe() {
		
	}
	
	public ContactMe(String firstName, String lastName,String email ,String country, String message) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.message = message;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ContactMe [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", country=" + country + ", message=" + message + "]";
	}
	
	
}
