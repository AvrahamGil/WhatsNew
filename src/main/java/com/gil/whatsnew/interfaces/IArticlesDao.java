package com.gil.whatsnew.interfaces;

import java.util.List;


import com.gil.whatsnew.bean.Article;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.NewsApiDataStructure;
import com.gil.whatsnew.bean.UserArticles;
import com.gil.whatsnew.exceptions.ApplicationException;

@Service
public interface IArticlesDao {

	public void addArticle(Article articles, String type) throws ApplicationException;
	
	public boolean updateArticle(NewsApiDataStructure articles) throws ApplicationException;
	
	public void deleteArticle(String type) throws ApplicationException;
		
	public List<Article> getAllArticles(String type) throws ApplicationException;
	
	public Article getArticleDetails(String type) throws ApplicationException;
	
	public void addFavoritArticle(UserArticles details) throws ApplicationException;
	
	public void deleteFavoritArticle(UserArticles details) throws ApplicationException;
	
	public boolean isArticleFavorated(String articleId, String userId) throws ApplicationException;
	
	public List<UserArticles>getFavoritArticles(String userId) throws ApplicationException;
	
}
