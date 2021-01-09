package com.gil.whatsnew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gil.whatsnew.bean.CatcherApi;
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.TechnologyLogic;

@RestController
@RequestMapping("/technology")
public class TechnologyApi {

	@Autowired
	private TechnologyLogic technologyLogic;
	
	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<ContextApi>getNewsArticles() throws ApplicationException {	
		return technologyLogic.getListOfAllNewsArticles();
	}
	
	@RequestMapping(value="/getOtherNewsArticles" , method = RequestMethod.GET)
	public List<CatcherApi> getOtherNewsArticles() throws ApplicationException {	
		return technologyLogic.getListOfOtherNewsArticles();
	}
	
	@RequestMapping(value="/getTechnologyRandomArticle" , method = RequestMethod.GET)
	public ContextApi getRandomArticle() throws ApplicationException {	
		return technologyLogic.getRandomArticles();
	}
}
