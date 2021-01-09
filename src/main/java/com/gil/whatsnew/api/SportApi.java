package com.gil.whatsnew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.SportLogic;

@RestController
@RequestMapping("/sport")
public class SportApi {

	@Autowired
	private SportLogic sportLogic;
	

	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<ContextApi>getNewsArticles() throws ApplicationException {	
		return sportLogic.getListOfAllNewsArticles();
	}
	
	@RequestMapping(value="/getNewYorkTimesNewsArticles" , method = RequestMethod.GET)
	public List<NewYorkTimesApi> getNewYorkTimesNewsArticles() throws ApplicationException {	
		return sportLogic.getListOfNewYorkTimesNewsArticles();
	}
	
	@RequestMapping(value="/getSportRandomArticle" , method = RequestMethod.GET)
	public ContextApi getRandomArticle() throws ApplicationException {	
		return sportLogic.getRandomArticles();
	}
	
}
