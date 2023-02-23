package com.gil.whatsnew.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.bean.UserArticles;
import com.gil.whatsnew.exceptions.ApplicationException;

@Service
public interface IArticlesDao {

	public void addArticle(Article articles,NewYorkTimesApi newYorkArticles,String type) throws ApplicationException;
	
	public void deleteArticle(String type) throws ApplicationException;
		
	public List<Article> getAllArticles(String type) throws ApplicationException;
	
	public List<NewYorkTimesApi> getNewYorkTimesArticles(String articleType,String type) throws ApplicationException;
	
	public Article getArticleDetails(String type) throws ApplicationException;
	
	public void addFavoritArticle(UserArticles details) throws ApplicationException;
	
	public boolean isArticleFavorated(String articleId, String userId) throws ApplicationException;
	
	public List<UserArticles>getFavoritArticles(String userId) throws ApplicationException;
	
}
