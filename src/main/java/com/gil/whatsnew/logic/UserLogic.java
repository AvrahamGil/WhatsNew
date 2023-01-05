package com.gil.whatsnew.logic;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.dao.UserDaoMongo;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.SessionUtils;


@Service
public class UserLogic {

	@Autowired
	private UserDaoMongo userDao;
	
	private String redirectToo = "https://whatsnew.me";
	
	private final String emailRegex = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	private final int max = 25;

	private HttpSession session;
	
	public void register(User user) throws ApplicationException {
		
		boolean detailsCorrect = true;
		
		String uuIdString = UUID.randomUUID().toString();
		
		if(user.getEmail() == null || user.getFullName() == null || user.getPassword() == null || user.getCountry() == null) throw new ApplicationException(ErrorType.Create_User_Failed,ErrorType.Create_User_Failed.getMessage(),false);
		
		try {
			String[] userString = user.toString().split(",");
			
			for(int i=1; i<= userString.length -1; i++) {
				
				detailsCorrect = userString[i].matches(emailRegex) == true ? userDao.isUserExist("email",user.getEmail()) == true ? false : true : true;
				detailsCorrect = userString[i].matches(emailRegex) == false ? userString[i].length() < max : detailsCorrect;
		
				if(detailsCorrect != true) break;
			}
			
			if(detailsCorrect != true) throw new ApplicationException(ErrorType.Create_User_Failed,ErrorType.Create_User_Failed.getMessage(),false); 
				
			while(userDao.isUserExist("userId",uuIdString) != false) uuIdString = UUID.randomUUID().toString();

			user.setUserId(uuIdString);
			
			User userAdded = userDao.addUser(user);
			
			if(userAdded == null) throw new ApplicationException(ErrorType.Create_User_Failed,ErrorType.Create_User_Failed.getMessage(),false);
			
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public HttpSession validateLoginDetails(String email, String password ,HttpServletRequest request) throws ApplicationException{
		try {
			boolean isCorrect = false;
			
			isCorrect = email.matches(emailRegex) && password.length() < max ? true : false;

			if(!isCorrect) return null;
			
			User user = userDao.getUserDetails(email);
			
			if(user == null) return null;
			
			session = SessionUtils.getSession(request,user.getEmail(),user.getPassword());
			
			if(session == null) return null;
			
			return session;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
	
	public void editUser(User user) throws ApplicationException {
		boolean detailsChanged = true;
		
		try {
			if(user == null) detailsChanged = false;
			
			if(!detailsChanged) throw new ApplicationException(ErrorType.Update_Failed,ErrorType.Update_Failed.getMessage(),false);
			
			User userDb = userDao.getUserDetails(user.getEmail()) != null ? user : null;
			
			if(userDb == null) detailsChanged = false;
			
			if(!detailsChanged) throw new ApplicationException(ErrorType.Update_Failed,ErrorType.Update_Failed.getMessage(),false);
			
			detailsChanged = user.equals(userDb) ? false : true;
			
			if(detailsChanged) userDao.editDetails(user);
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public List<String>listOfUsers() throws ApplicationException{
		List<String>details = new ArrayList<String>();
		boolean usersExist = true;
		
		try {
			List<User>users = userDao.getUsers();
			
			if(users == null) usersExist = false;
			
			if(!usersExist) throw new ApplicationException(ErrorType.Get_List_Failed,ErrorType.Get_List_Failed.getMessage(),false);
			
			for(User detail : users) {
				details.add(detail.getFullName());
				details.add(detail.getCountry());
			}
			
			return details;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
	
	public void logout(String email, String password ,HttpServletRequest request) throws ApplicationException{
		RequestDispatcher requestDispatcher = null;
		
		try {
			if(request != null) {
				if(request.getSession(false) != null || !session.getAttribute("token").toString().equals(null)) {
					requestDispatcher = request.getRequestDispatcher(redirectToo);
					session.invalidate();
					request = null;
				}
			}
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.General_Error,ErrorType.General_Error.getMessage(),false);
		}
	}
}
