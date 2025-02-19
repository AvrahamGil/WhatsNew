package com.gil.whatsnew.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("contact_me")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContactMe {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String country;
	private String message;
	

	public ContactMe(String firstName, String lastName,String email ,String country, String message) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.message = message;
	}

}
