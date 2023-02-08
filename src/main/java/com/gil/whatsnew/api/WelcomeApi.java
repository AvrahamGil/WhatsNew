package com.gil.whatsnew.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.gil.whatsnew.utils.TokenBuilder;

@RestController
@RequestMapping("/welcomeapi")
public class WelcomeApi {

	@Autowired
	private UserLogic userLogic;
	
	@RequestMapping(value="/register" , method = RequestMethod.POST)
	public void createUser(@RequestBody User user , HttpServletRequest request) throws Exception {		
		try {
			if(user != null) userLogic.register(user);
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	@RequestMapping(value = "/login" , method=RequestMethod.POST)
	public void login(@RequestParam String token,@RequestBody Login loginDetail,HttpServletRequest request) throws ApplicationException {
		try {
			if(loginDetail != null) {
				if(userLogic.validateLoginDetails(loginDetail.getEmail(), loginDetail.getPassword(), request) == null) {
					throw new ApplicationException(ErrorType.General_Error,"One or more details are incorrect",true);
				}
			}
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	@RequestMapping(value = "/token" , method=RequestMethod.GET)
	public String generateCSRFToekn(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			return userLogic.generateCSRFToken(request,response);

		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
}
