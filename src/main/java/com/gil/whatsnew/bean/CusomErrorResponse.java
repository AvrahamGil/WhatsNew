package com.gil.whatsnew.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CusomErrorResponse {

	private int status;
	private String errorMessage;

}
