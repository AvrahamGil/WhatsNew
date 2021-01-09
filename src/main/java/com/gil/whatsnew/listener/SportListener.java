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
import com.gil.whatsnew.enums.SportType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.SportLogic;

@Service
public class SportListener implements ApplicationListener<SportEvent>{

	@Autowired
	private SportLogic sportLogic;
	
	@Override
	public void onApplicationEvent(SportEvent event) {
		List<SportType> types = new ArrayList<SportType>();
		Set<ContextApi>articles = new HashSet<ContextApi>();
		Set<NewYorkTimesApi>newYorkArticles = new HashSet<NewYorkTimesApi>();
		
		sportLogic.addingTypesSites(types);
		
		try {
			switch(event.getEventType()) {
			case Get:
				articles = getContextApiArticles(types);
				newYorkArticles = getNewYorkTimesApiArticles();
			case Delete:
				sportLogic.deleteArticles();
			case Add:
				sportLogic.addArticlesToStock(articles);
				sportLogic.addNewYorkTimesArticles(newYorkArticles);
				break;
		
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	private Set<ContextApi>getContextApiArticles(List<SportType> types) {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		Set<ContextApi>uniqueArticles = new HashSet<ContextApi>();
		
		try {
			for(SportType type : types) {
				articles = sportLogic.getArticles(type);
				
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
			newYorkTimesArticles = sportLogic.getNewYorkArticles();
			
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
