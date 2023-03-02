package com.gil.whatsnew.logic;

import java.util.ArrayList;

import java.util.List;

import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.dao.UserDaoMongo;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.Authentication;

@Service
public class UserLogic {

	@Autowired
	private UserDaoMongo userDao;

	@Autowired
	private Authentication authentication;

	private String redirectToo = "https://whatsnew.me";

	private final String emailRegex = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	private final String specialChar = "~`!#$%^&*(){}[]+-_=/><:\"\\?;";

	private final int max = 25;

	private HttpSession session;

	public void register(HttpServletRequest request, User user) throws ApplicationException {

		boolean detailsCorrect = true;
		boolean isCorrect = true;

		String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();

		if (user.getEmail() == null || user.getFullName() == null || user.getPassword() == null
				|| user.getCountry() == null)
			isCorrect = false;

		if (!authentication.verifyCaptcha(request))
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
				detailsCorrect = userString[i].matches(emailRegex) == false ? userString[i].length() < max
						: detailsCorrect;

				if (detailsCorrect != true)
					isCorrect = false;
			}

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

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}

	public ResponseEntity<Object> validateLoginDetails(HttpServletRequest request, String email, String password)
			throws ApplicationException {
		boolean detailsCorrect = true;
		boolean isCorrect = true;
		ResponseEntity<Object> res = null;

		try {
			if (email == null || password == null)
				isCorrect = false;

			detailsCorrect = email.matches(emailRegex) && password.length() < max ? true : false;

			if (!detailsCorrect)
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.Login_Failed, ErrorType.Login_Failed.getMessage(), false);

			if (userDao.getUserDetails(email) == null)
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.User_Not_Exist, ErrorType.User_Not_Exist.getMessage(), false);

			if (!authentication.verifyCaptcha(request))
				isCorrect = false;

			if (!authentication.verifyCSRFToken(request))
				isCorrect = false;

			if (!isCorrect)
				throw new ApplicationException(ErrorType.Login_Failed, ErrorType.Login_Failed.getMessage(), false);

			res = authentication.getConnection(email, password);

			return res;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return res;
	}

	public void editUser(User user) throws ApplicationException {
		boolean detailsChanged = true;

		try {
			if (user == null)
				detailsChanged = false;

			if (!detailsChanged)
				throw new ApplicationException(ErrorType.Update_Failed, ErrorType.Update_Failed.getMessage(), false);

			User userDb = userDao.getUserDetails(user.getEmail()) != null ? user : null;

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
				if (cookie.getName().equals("X-TOKEN")) {
					if (cookie.getValue().length() != 0) {
						requestDispatcher = request.getRequestDispatcher(redirectToo);
						cookie.setValue("empty");
						cookie.setMaxAge(0);
					}
				}

				if (cookie.getName().equals("X-CSRFTOKEN")) {
					if (cookie.getValue().length() != 0) {
						requestDispatcher = request.getRequestDispatcher(redirectToo);
						cookie.setValue("empty");
						cookie.setMaxAge(0);
					}
				}
			}

			ResponseEntity<Object> res = new ResponseEntity<Object>(cookies, HttpStatus.OK);

			return res;

		} catch (Exception e) {
			throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
		}
	}

	public String generateCSRFToken(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException {
		try {
			String token = authentication.generateCSRFToken();

			Cookie cookie = new Cookie("X-CSRFTOKEN", token);
			response.addCookie(cookie);

			return token;

		} catch (Exception e) {
			throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
		}
	}

}
