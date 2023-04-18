package com.gil.whatsnew.dao;

import java.util.List;



import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.gil.whatsnew.bean.ContactMe;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.interfaces.IUserDao;
import com.mongodb.client.result.UpdateResult;

@Repository
public class UserDao extends IUserDao {
	
	@Override
	public User addUser(User user) throws ApplicationException {
		try {		
			User users = user != null ? mongoTemplate.save(user) : null;

			if(users == null) return null;
			
			System.out.println("User " + user.getUserId() + " added successfully");
			
			return user;
			
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.Create_Failed,ErrorType.Create_Failed.getMessage(),false);
		}
	}

	@Override
	public boolean isUserExist(String key,String value) throws ApplicationException {	
		Query query = new Query();
		query.addCriteria(Criteria.where(key).is(value));
	
		try {
			User user = mongoTemplate.findOne(query, User.class);
			
			if(user != null) return true;
			
			return false;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.User_Already_Exist,ErrorType.User_Already_Exist.getMessage(),false);
		} finally {
			query = null;
		}
	}
	
	@Override
	public boolean editDetails(User user) throws ApplicationException {
		try {
			Query query = new Query();
			query = Query.query(Criteria.where("email").is(user.getEmail()));
			
			Update details = new Update().set("image", user.getImage());
			UpdateResult result = user != null ? mongoTemplate.updateFirst(query,details,User.class) : null;

			if(result.getMatchedCount() == 0) return false;
			
			System.out.println("User " + user.getUserId() + " details updated successfully");
			
			return true;
			
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.Update_Failed,ErrorType.Update_Failed.getMessage(),false);
		}
	}


	@Override
	public User getUserDetails(String email,String password) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("email").is(email).and("password").is(password));
	
		try {
			User user = mongoTemplate.findOne(query, User.class);
			
			if(user == null) return null;
			
			return user;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.User_Details,ErrorType.User_Details.getMessage(),false);
		}
	}

	@Override
	public User getUser(String email,String userId) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("email").is(email).and("_id").is(userId));
	
		try {
			User user = mongoTemplate.findOne(query, User.class);
			
			if(user == null) return null;
			
			return user;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.User_Details,ErrorType.User_Details.getMessage(),false);
		}
	}
	
	@Override
	public ContactMe addMessage(ContactMe contactMe) throws ApplicationException {
		try {		
			ContactMe reader = contactMe != null ? mongoTemplate.save(contactMe) : null;

			if(reader == null) return null;
			
			return reader;
			
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.Create_Failed,ErrorType.Create_Failed.getMessage(),false);
		}
	}
	@Override
	public List<User> getUsers() throws ApplicationException {
		try {
			List<User> users = mongoTemplate.findAll(User.class);
			
			if(!users.isEmpty()) return users;
			
			return null;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.Get_List_Failed,ErrorType.Get_List_Failed.getMessage(),false);
		}
	}

	
}
