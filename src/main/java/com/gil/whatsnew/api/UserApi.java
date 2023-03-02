package com.gil.whatsnew.api;

import java.util.ArrayList;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gil.whatsnew.bean.Login;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.UserLogic;
import com.gil.whatsnew.utils.Authentication;


@RestController
@RequestMapping("/rest/api//user")
public class UserApi {

	@Autowired
	private UserLogic userLogic;
	
	@Autowired
	private Authentication authentication;
	
	@RequestMapping(value = "/logout" , method=RequestMethod.POST)
	public ResponseEntity<Object> logOut(@RequestBody Login loginDetail,HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		try {
			if(!authentication.verifyCookies(request)) throw new ApplicationException(ErrorType.General_Error,"One or more details are incorrect",true);
			
			ResponseEntity<Object> res = userLogic.logout(request);
				
			if(res == null) throw new ApplicationException(ErrorType.General_Error,"One or more details are incorrect",true);
				
			return new ResponseEntity<Object>(res, HttpStatus.OK);
			
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value="/editDetails" , method = RequestMethod.PUT)
	public void update(@RequestBody User user,@RequestBody Login loginDetail,HttpServletRequest request , HttpServletResponse response) throws ApplicationException {
		
		try {
			if(authentication.verifyCookies(request)) {
				if(user != null) userLogic.editUser(user);
			}

		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

	}
	
	@RequestMapping(value="/users" , method = RequestMethod.GET)
	public List<String> userList(@RequestBody Login loginDetail,HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

		List<String>users = new ArrayList<String>();
		
		try {
			if(authentication.verifyCookies(request)) {
				users = userLogic.listOfUsers();
			}

			if(!users.isEmpty()) return users;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
}
