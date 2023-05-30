package com.gil.whatsnew.logic;

import java.io.IOException;

import java.util.ArrayList;


import java.util.List;

import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.gil.whatsnew.bean.ContactMe;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.dao.UserDao;
import com.gil.whatsnew.enums.Cookies;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.Authentication;

@Service
public class UserLogic {

	@Autowired
	private UserDao userDao;

	@Autowired
	private Authentication authentication;

	private String redirectToo = "https://whatsnew.me";

	private final String emailRegex = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	private final String specialChar = "~`!#$%^&*(){}[]+-_=/><:\"\\?;";

	private String[] imageExtensions = {".jpg",".png","jpe","jpeg"};
	
	private final int max = 15;

	public ResponseEntity<Object> register(HttpServletRequest request,HttpServletResponse response ,User user) throws ApplicationException {

		boolean detailsCorrect = true;
		boolean isCorrect = true;
		ResponseEntity<Object> res;
		String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();

		if (user.getEmail() == null || user.getFullName() == null || user.getPassword() == null
				|| user.getCountry() == null)
			isCorrect = false;

		if (!isCorrect)
			throw new ApplicationException(ErrorType.Create_User_Failed, ErrorType.Create_User_Failed.getMessage(),
					false);

		try {
			String[] userString = user.toString().split(",");

			for (int i = 1; i <= userString.length - 1; i++) {

				detailsCorrect = userString[i].matches(emailRegex) == true && !userString[i].contains(specialChar)
						? userDao.isUserExist("email", user.getEmail()) == true ? false : true
						: true;
				detailsCorrect = userString[i].matches(emailRegex) == false ? userString[i].length() <= max && 2 <= userString[i].length()
						: detailsCorrect;

				if (detailsCorrect != true)
					isCorrect = false;
			}

			if(authentication.isCountryExist(user.getCountry()) == false)
				isCorrect = false;
			
			if (!isCorrect)
				throw new ApplicationException(ErrorType.Create_User_Failed, ErrorType.Create_User_Failed.getMessage(),
						false);

			user.setUserId(uuid);

			User userAdded = userDao.addUser(user);

			if (userAdded == null)
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.Create_User_Failed, ErrorType.Create_User_Failed.getMessage(),
						false);
			
			res = authentication.getConnection(userAdded, 5, response);
			
			return res;
			
		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}

	public ResponseEntity<Object> validateDetails(HttpServletRequest request,HttpServletResponse response ,String email, String password)
			throws ApplicationException {
		ResponseEntity<Object> res;
		boolean detailsCorrect = true;
		boolean isCorrect = true;

		try {
			if (email == null || password == null)
				isCorrect = false;

			detailsCorrect = email.matches(emailRegex) && password.length() < max ? true : false;

			if (!detailsCorrect)
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);;

			User user = userDao.getUserDetails(email,password);
			
			if (user == null)
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);;

			if (!authentication.verifyCSRFToken(request))
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);;

			res = authentication.getConnection(user,15,response);
			
			return res;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}

	public void editUser(User user) throws ApplicationException {
		boolean detailsChanged = true;

		try {
			if (user == null)
				detailsChanged = false;

			if (!detailsChanged)
				throw new ApplicationException(ErrorType.Update_Failed, ErrorType.Update_Failed.getMessage(), false);

			User userDb = userDao.getUserDetails(user.getEmail(),user.getPassword()) != null ? user : null;

			if (userDb == null)
				detailsChanged = false;

			if (!detailsChanged)
				throw new ApplicationException(ErrorType.Update_Failed, ErrorType.Update_Failed.getMessage(), false);

			detailsChanged = user.equals(userDb) ? false : true;

			if (detailsChanged)
				userDao.editDetails(user);

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}

	public boolean saveMessage(String firstName,String lastName, String email, String country, String message) throws ApplicationException {
		boolean detailsProve = true;
		int none = 0;
		String[] details = new String[5];
		details[0] = firstName;
		details[1] = lastName;
		details[2] = email;
		details[3] = country;
		details[4] = message;
		
	
		try {
			for(String detail : details) {
				if(detail.length() == none) return false;
				
				if(detail.contains(specialChar)) detailsProve = false;
				
				if(details[2] == detail) {
					if(detail.contains(emailRegex)) detailsProve = false;
				}
				
				if(details[3] == detail) {
					if(!authentication.isCountryExist(country)) detailsProve = false;
				}
			}
			
			if(!detailsProve)
				throw new ApplicationException(ErrorType.Update_Failed, ErrorType.Update_Failed.getMessage(), false);
			
			ContactMe contact = new ContactMe(firstName,lastName,email,country,message);
			userDao.addMessage(contact);
			
			return true;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		
		return false;
	}
	public List<String> listOfUsers() throws ApplicationException {
		List<String> details = new ArrayList<String>();
		boolean usersExist = true;

		try {
			List<User> users = userDao.getUsers();

			if (users == null)
				usersExist = false;

			if (!usersExist)
				throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(),
						false);

			for (User detail : users) {
				details.add(detail.getFullName());
				details.add(detail.getCountry());
			}

			return details;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}

	public ResponseEntity<Object> logout(HttpServletRequest request) throws ApplicationException {
		RequestDispatcher requestDispatcher = null;

		try {
			Cookie[] cookies = request.getCookies();

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Cookies.XTOKEN.getName())) {
					if (cookie.getValue().length() != 0) {
						requestDispatcher = request.getRequestDispatcher(redirectToo);
						cookie.setValue("empty");
						cookie.setMaxAge(0);
					}
				}

				if (cookie.getName().equals(Cookies.XCSRFTOKEN.getName())) {
					if (cookie.getValue().length() != 0) {
						requestDispatcher = request.getRequestDispatcher(redirectToo);
						cookie.setValue("empty");
						cookie.setMaxAge(0);
					}
				}
			}


			ResponseEntity<Object> res = new ResponseEntity<Object>(null,HttpStatus.OK);
			
			return res;

		} catch (Exception e) {
			throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
		}
	}

	public String generateCSRFToken(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		try {
			String token = authentication.generateCSRFToken();

			Cookie cookie = new Cookie(Cookies.XCSRFTOKEN.getName(), token);
			response.addCookie(cookie);

			return token;

		} catch (Exception e) {
			throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
		}
	}

	public User store(MultipartFile file,String email,String userId) throws ApplicationException {
		
		boolean isImage = true;
		boolean isNameEndingWithExtension = false;
		
		byte[] maxBytes = new byte[3000000];
				
		try {
			if(file.getBytes().length > maxBytes.length) isImage = false;
			
			if(!isImage) throw new ApplicationException(ErrorType.General_Error,ErrorType.General_Error.getMessage(),
					false);
			
			for(String extension : imageExtensions) {
				if(file.getOriginalFilename().endsWith(extension)) {
					isNameEndingWithExtension = true;
				}
			}
			
			if(!isNameEndingWithExtension) isImage = false;
			
			User user = userDao.getUser(email, userId);
			
			if(!isImage) throw new ApplicationException(ErrorType.General_Error,ErrorType.General_Error.getMessage(),
					false);
			
			if(user.isWithImage()) user.setImage(null);

			user.setImage(file.getBytes());
			
			userDao.editDetails(user);
			
			user.setUserId(null);
			user.setPassword(null);

			return user;
			
		}catch(IOException e) {
			 throw new ApplicationException(ErrorType.General_Error,ErrorType.General_Error.getMessage(),
						false);
		} catch(MaxUploadSizeExceededException e) {
			throw new ApplicationException(ErrorType.Maximum_Size,ErrorType.Maximum_Size.getMessage(),
					false);
		}
	}
	
	public Resource loadAsResource(String filename) {
		return null;
	}
}
