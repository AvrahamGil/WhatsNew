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
import com.gil.whatsnew.enums.BusinessType;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.logic.BusinessLogic;

@Service
public class BusinessListener implements ApplicationListener<BusinessEvent>{

	@Autowired
	private BusinessLogic businessLogic;
	
	
	@Override
	public void onApplicationEvent(BusinessEvent event) {
		List<BusinessType> types = new ArrayList<BusinessType>();
		Set<ContextApi>articles = new HashSet<ContextApi>();

		businessLogic.addingTypesSites(types);
		
		try {
			switch(event.getEventType()) {
			case Get:
				articles = getContextApiArticles(types);
			case Delete:
				businessLogic.deleteArticles();
			case Add:
				businessLogic.addArticlesToStock(articles);
				break;
		
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private Set<ContextApi>getContextApiArticles(List<BusinessType> types) {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		Set<ContextApi>uniqueArticles = new HashSet<ContextApi>();
		
		try {
			for(BusinessType type : types) {
				articles = businessLogic.getArticles(type);
				
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
