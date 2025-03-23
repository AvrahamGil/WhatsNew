package com.gil.whatsnew.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document("users")
@AllArgsConstructor
@Getter
@Setter
@ToString
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
}
