package com.gil.whatsnew.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.Login;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.UserLogic;
import com.gil.whatsnew.utils.SessionUtils;

@RestController
@RequestMapping("/rest/api//user")
public class UserApi {

	@Autowired
	private UserLogic userLogic;
	

	@RequestMapping(value = "/logout" , method=RequestMethod.POST)
	public void logOut(@RequestBody Login loginDetail,HttpServletRequest request) throws ApplicationException {

		try {
			if(SessionUtils.getSession(request,loginDetail.getEmail(),loginDetail.getPassword()) != null) {
				userLogic.logout(loginDetail.getEmail(),loginDetail.getPassword(), request);
			}
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	@RequestMapping(value="/editDetails" , method = RequestMethod.PUT)
	public void update(@RequestBody User user,HttpServletRequest request) throws ApplicationException {
		
		try {
			if(SessionUtils.getSession(request,user.getEmail(),user.getPassword()) != null) {
				if(user != null) userLogic.editUser(user);
			}

		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

	}
	
	@RequestMapping(value="/users" , method = RequestMethod.GET)
	public List<String> userList(@RequestBody Login loginDetail,HttpServletRequest request) throws ApplicationException {

		List<String>users = new ArrayList<String>();
		
		try {
			if(SessionUtils.getSession(request,loginDetail.getEmail(),loginDetail.getPassword()) != null) {
				users = userLogic.listOfUsers();
			}

			if(!users.isEmpty()) return users;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
}
