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
import com.gil.whatsnew.enums.BusinessType;
import com.gil.whatsnew.enums.TravelType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.TravelLogic;

@Service
public class TravelListener implements ApplicationListener<TravelEvent>{

	@Autowired
	private TravelLogic travelLogic;
	
	@Override
	public void onApplicationEvent(TravelEvent event) {
		List<TravelType> types = new ArrayList<TravelType>();
		Set<ContextApi>articles = new HashSet<ContextApi>();

		travelLogic.addingTypesSites(types);
		
		try {
			switch(event.getEventType()) {
			case Get:
				articles = getContextApiArticles(types);
			case Delete:
				travelLogic.deleteArticles();
			case Add:
				travelLogic.addArticlesToStock(articles);
				break;
		
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private Set<ContextApi>getContextApiArticles(List<TravelType> types) {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		Set<ContextApi>uniqueArticles = new HashSet<ContextApi>();
		
		try {
			for(TravelType type : types) {
				articles = travelLogic.getArticles(type);
				
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
}
