package com.gil.whatsnew.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gil.whatsnew.bean.CusomErrorResponse;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{

	@Autowired
	private CusomErrorResponse customErrorResponse;
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<CusomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

		customErrorResponse.setErrorMessage(ex.getMessage());
		customErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);

	}
}
