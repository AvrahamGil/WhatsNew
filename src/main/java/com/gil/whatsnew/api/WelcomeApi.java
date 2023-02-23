package com.gil.whatsnew.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.Login;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.UserLogic;
import com.gil.whatsnew.utils.Authentication;

@RestController
@RequestMapping("/welcome")
public class WelcomeApi {

	@Autowired
	private UserLogic userLogic;
	
	@RequestMapping(value="/register" , method = RequestMethod.POST)
	public void createUser(@RequestBody User user , HttpServletRequest request) throws Exception {		
		try {
			if(user != null) userLogic.register(request,user);
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	@RequestMapping(value = "/login" , method=RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody Login loginDetail,HttpServletRequest request,HttpServletResponse response) throws ApplicationException {
		try {
			ResponseEntity<Object> res = userLogic.validateLoginDetails(request,loginDetail.getEmail(), loginDetail.getPassword()); 
			if(res == null) {
				throw new ApplicationException(ErrorType.General_Error,"One or more details are incorrect",true);
			}
			
			return new ResponseEntity<Object>(res, HttpStatus.OK);
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);
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
