package com.gil.whatsnew.interfaces;



import java.sql.Date;
import java.util.List;
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.exceptions.ApplicationException;

public interface IArticlesDao {

	public void addArticle(ContextApi articles) throws ApplicationException;
	
	public void deleteArticle() throws ApplicationException;
		
	List<ContextApi> getAllArticles() throws ApplicationException;
	
	
}
