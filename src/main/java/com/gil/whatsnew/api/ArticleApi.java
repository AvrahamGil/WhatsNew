package com.gil.whatsnew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.ArticleLogic;

@RestController
@RequestMapping("/articles")
public class ArticleApi {

	@Autowired
	private ArticleLogic article;
	
	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<List<Article>>getNewsArticles() throws ApplicationException {	
		return article.getListOfNewsArticles();
	}
	
	@RequestMapping(value="/getNewYorkTimesNewsArticles" , method = RequestMethod.GET)
	public List<NewYorkTimesApi> getNewYorkTimesNewsArticles() throws ApplicationException {
		return article.getListOfNewYorkTimesArticles();
	}
	
	@RequestMapping(value="/getNewsRandomArticle" , method = RequestMethod.GET)
	public List<Article> getRandomArticle() throws ApplicationException {	
		return article.getRandomArticles();
	}
}
