package com.gil.whatsnew.api;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gil.whatsnew.bean.Login;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.UserLogic;


@RestController
@RequestMapping("/welcome")
public class WelcomeApi {

	@Autowired
	private UserLogic userLogic;
	
	@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "true",allowCredentials="true")
	@RequestMapping(value="/register" , method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody User user ,HttpServletRequest request,HttpServletResponse response) throws Exception {		
		try {
			if(user != null) return userLogic.register(request,response,user);
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
	
	
	@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "true",allowCredentials="true")
	@RequestMapping(value = "/login" , method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Login loginDetail,HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			return userLogic.validateDetails(request,response,loginDetail.getEmail(), loginDetail.getPassword());
		
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
	
	@RequestMapping(value = "/csrftoken" , method=RequestMethod.GET)
	public String generateCSRFToekn(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			return userLogic.generateCSRFToken(request,response);

		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
}
