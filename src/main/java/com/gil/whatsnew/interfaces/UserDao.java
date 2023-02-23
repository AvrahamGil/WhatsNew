package com.gil.whatsnew.interfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.exceptions.ApplicationException;

public abstract class UserDao {

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	protected abstract User addUser(User user) throws ApplicationException;
	
	protected abstract boolean editDetails(User user) throws ApplicationException;
	
	protected abstract User getUserDetails(String email) throws ApplicationException;
	
	protected abstract List<User>getUsers() throws ApplicationException;

	protected abstract boolean isUserExist(String userId,String email) throws ApplicationException;
	
}
