package com.gil.whatsnew.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;

@Service
public interface IArticlesDao {

	public void addArticle(Article articles,NewYorkTimesApi newYorkArticles,String type) throws ApplicationException;
	
	public void deleteArticle(String type) throws ApplicationException;
		
	List<Article> getAllArticles(String type) throws ApplicationException;
	
	public List<NewYorkTimesApi> getNewYorkTimesArticles(String articleType,String type) throws ApplicationException;
	
	public List<Article> getRandomArticle(String type) throws ApplicationException;
	
}
