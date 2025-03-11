package com.gil.whatsnew.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ErrorLog {

	private String message;
	private int statusCode;

}
