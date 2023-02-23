package com.gil.whatsnew.api;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.Login;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.ArticleLogic;
import com.gil.whatsnew.logic.UserLogic;
import com.gil.whatsnew.utils.Authentication;


@RestController
@RequestMapping("/articles")
public class ArticleApi {

	@Autowired
	private ArticleLogic article;
	
	@Autowired
	private Authentication authentication;
	
	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<List<Article>>getNewsArticles() throws ApplicationException {	
		List<List<Article>>articles = article.getListOfNewsArticles();
		if(articles != null) return articles;
		
		return null;
	}
	
	@RequestMapping(value="/getNewYorkTimesNewsArticles" , method = RequestMethod.GET)
	public List<NewYorkTimesApi> getNewYorkTimesNewsArticles() throws ApplicationException {
		List<NewYorkTimesApi>articles = article.getListOfNewYorkTimesArticles();
		if(articles != null) return articles;
		
		return null;
	}
	
	@RequestMapping(value="/liked" , method = RequestMethod.POST)
	public void addToFavorit(@PathVariable String articleId , HttpServletRequest request , HttpServletResponse response , @RequestBody Login loginDetail) throws ApplicationException {	
			try {
				if(authentication.verifyCookies(request)) {
					article.addIntoFavorit(articleId, request);
				}
				
			}catch(ApplicationException e) {
				ExceptionHandler.generatedLogicExceptions(e);
			}
	}
	
	@RequestMapping(value="/favoritArticles" , method = RequestMethod.GET)
	public List<Article> getFavoritArticles(@RequestBody Login loginDetail,HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
		try {
			if(authentication.verifyCookies(request)) {
				article.getFavoritArticles(request);
			}
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		
		return null;
	}
}
