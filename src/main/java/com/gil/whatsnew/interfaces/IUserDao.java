package com.gil.whatsnew.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.gil.whatsnew.bean.ContactMe;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.exceptions.ApplicationException;

public abstract class IUserDao {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	protected abstract User addUser(User user) throws ApplicationException;
	
	protected abstract boolean editDetails(User user) throws ApplicationException;
	
	protected abstract User getUserDetails(String email,String password) throws ApplicationException;
	
	protected abstract User getUser(String email,String userId) throws ApplicationException;
	
	protected abstract ContactMe addMessage(ContactMe contactMe) throws ApplicationException;
	
	protected abstract List<User>getUsers() throws ApplicationException;

	protected abstract boolean isUserExist(String userId,String email) throws ApplicationException;
	
}
