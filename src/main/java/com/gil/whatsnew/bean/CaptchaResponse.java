package com.gil.whatsnew.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaptchaResponse {

	private Boolean success;

	private Date timestamp;

	private String hostname;

	@JsonProperty("error-codes")
	private List<String> errorCodes;

	    
	    
}
