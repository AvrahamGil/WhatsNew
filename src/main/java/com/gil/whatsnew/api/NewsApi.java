package com.gil.whatsnew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.bean.News;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.NewsLogic;

@RestController
@RequestMapping("/news")
public class NewsApi {

	@Autowired
	private NewsLogic newsLogic;
	

	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<ContextApi>getNewsArticles() throws ApplicationException {	
		return newsLogic.getListOfAllNewsArticles();
	}
	
	@RequestMapping(value="/getNewYorkTimesNewsArticles" , method = RequestMethod.GET)
	public List<NewYorkTimesApi> getNewYorkTimesNewsArticles() throws ApplicationException {	
		return newsLogic.getListOfNewYorkTimesNewsArticles();
	}
	
	@RequestMapping(value="/getNewsRandomArticle" , method = RequestMethod.GET)
	public ContextApi getRandomArticle() throws ApplicationException {	
		return newsLogic.getRandomArticles();
	}
}
