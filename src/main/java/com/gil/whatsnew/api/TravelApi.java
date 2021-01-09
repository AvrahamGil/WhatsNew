package com.gil.whatsnew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.TravelLogic;

@RestController
@RequestMapping("/travel")
public class TravelApi {

	@Autowired
	private TravelLogic travelLogic;
	
	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<ContextApi>getNewsArticles() throws ApplicationException {	
		return travelLogic.getListOfAllNewsArticles();
	}
	
	@RequestMapping(value="/getTravelRandomArticle" , method = RequestMethod.GET)
	public ContextApi getRandomArticle() throws ApplicationException {	
		return travelLogic.getRandomArticles();
	}
}
