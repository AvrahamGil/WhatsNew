package com.gil.whatsnew.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.NewsLogic;

@Service
public class NewsListener implements ApplicationListener<NewsEvent>{

	@Autowired
	private NewsLogic newsLogic;
	
	@Override
	public void onApplicationEvent(NewsEvent event) {
		List<NewsType> types = new ArrayList<NewsType>();
		Set<ContextApi>articles = new HashSet<ContextApi>();
		Set<NewYorkTimesApi>newYorkArticles = new HashSet<NewYorkTimesApi>();
		
		newsLogic.addingTypesSites(types);
		
		try {
			switch(event.getEventType()) {
			case Get:
				articles = getContextApiArticles(types);
				newYorkArticles = getNewYorkTimesApiArticles();
			case Delete:
				newsLogic.deleteArticles();
			case Add:
				newsLogic.addArticlesToStock(articles);
				newsLogic.addNewYorkTimesArticles(newYorkArticles);
				break;
		
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	
		
	}
	
	private Set<ContextApi>getContextApiArticles(List<NewsType> types) {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		Set<ContextApi>uniqueArticles = new HashSet<ContextApi>();
		
		try {
			for(NewsType type : types) {
				articles = newsLogic.getArticles(type);
				
				for(ContextApi article : articles) {
					if(!uniqueArticles.contains(article)) {
						uniqueArticles.add(article);
					}
				}
			}
			
			return uniqueArticles;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return uniqueArticles;
	}
	
	private Set<NewYorkTimesApi>getNewYorkTimesApiArticles() {
		List<NewYorkTimesApi> newYorkTimesArticles = new ArrayList<NewYorkTimesApi>();
		Set<NewYorkTimesApi> uniqueNewYorkTimesArticles = new HashSet<NewYorkTimesApi>();
		
		try {
			newYorkTimesArticles = newsLogic.getNewYorkArticles();
			
			for (NewYorkTimesApi article : newYorkTimesArticles) {
				if(!uniqueNewYorkTimesArticles.contains(article)) {
					uniqueNewYorkTimesArticles.add(article);
				}
			}
			
			return uniqueNewYorkTimesArticles;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return uniqueNewYorkTimesArticles;
	} 

}
