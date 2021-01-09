package com.gil.whatsnew.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.CatcherApi;
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.enums.TechnologyType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.TechnologyLogic;

@Service
public class TechnologyListener implements ApplicationListener<TechnologyEvent>{

	@Autowired
	private TechnologyLogic technologyLogic;
	
	@Override
	public void onApplicationEvent(TechnologyEvent event) {
		List<TechnologyType> types = new ArrayList<TechnologyType>();
		Set<ContextApi>articles = new HashSet<ContextApi>();
		Set<CatcherApi>catcherArticles = new HashSet<CatcherApi>();
		
		technologyLogic.addingTypesSites(types);
		
		try {
			switch(event.getEventType()) {
			case Get:
				articles = getContextApiArticles(types);
				catcherArticles = getCatcherApiArticles();
			case Delete:
				technologyLogic.deleteArticles();
			case Add:
				technologyLogic.addArticlesToStock(articles);
				technologyLogic.addCatcherArticles(catcherArticles);
				break;
		
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private Set<ContextApi>getContextApiArticles(List<TechnologyType> types) {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		Set<ContextApi>uniqueArticles = new HashSet<ContextApi>();
		
		try {
			for(TechnologyType type : types) {
				articles = technologyLogic.getArticles(type);
				
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
	
	private Set<CatcherApi>getCatcherApiArticles() {
		List<CatcherApi> chatcherArticles = new ArrayList<CatcherApi>();
		Set<CatcherApi> uniqueCatcherArticles = new HashSet<CatcherApi>();
		List<TechnologyType>types = new ArrayList<TechnologyType>();
		
		types.add(TechnologyType.Apple);
		types.add(TechnologyType.Bitcoin);
		types.add(TechnologyType.TechRadar);
		
		try {
			for(TechnologyType type : types) {
				chatcherArticles = technologyLogic.getOtherArticles(type);
				
				for (CatcherApi article : chatcherArticles) {
					if(!uniqueCatcherArticles.contains(article)) {
						uniqueCatcherArticles.add(article);
					}
				}
			}
			
			
			return uniqueCatcherArticles;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return uniqueCatcherArticles;
	} 
}
