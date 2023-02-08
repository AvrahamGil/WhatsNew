package com.gil.whatsnew.dao;

import java.util.List;




import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.interfaces.IUserDao;
import com.mongodb.client.result.UpdateResult;

@Repository
public class UserDaoMongo extends IUserDao {
	
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
			Update details = new Update().set("password", user.getPassword()).addToSet("fullName", user.getFullName());
			UpdateResult result = user != null ? mongoTemplate.updateFirst(query,details,User.class) : null;

			if(result == null) return false;
			
			System.out.println("User " + user.getUserId() + " details updated successfully");
			
			return true;
			
		} catch(Exception e) {
			throw new ApplicationException(ErrorType.Update_Failed,ErrorType.Update_Failed.getMessage(),false);
		}
	}

	@Override
	public User getUserDetails(String email) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("email").is(email));
	
		try {
			User user = mongoTemplate.findOne(query, User.class);
			
			if(user == null) return null;
			
			return user;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.User_Details,ErrorType.User_Details.getMessage(),false);
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
