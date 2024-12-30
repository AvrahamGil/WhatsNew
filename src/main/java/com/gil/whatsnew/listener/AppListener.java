package com.gil.whatsnew.listener;


import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.ArticleLogic;


@Component
public class AppListener implements ApplicationListener<AppEvent> {

	@Autowired
	private ArticleLogic articleLogic;

	private final String[] categories = {"news","business","sport", "technology", "travel" };

	@Override
	public void onApplicationEvent(AppEvent event) {
		System.out.println("Getting categories ready to extracting articles");
		List<String> source;
		
		Set<Article> articles = new HashSet<Article>();

		boolean articlesDeleted = false;
		
		try {
			for (String category : categories) {
				source = new ArrayList<String>();

				source = articleLogic.addingSources(source, category);

				if(!articlesDeleted) {
					articleLogic.deleteArticles(source);
				}
				
				articles = articleLogic.getApiArticles(source);
				
				articleLogic.addArticlesToStock(articles, category);

				articles.clear();
			}

		} catch (ApplicationException e) {
			ExceptionHandler.generatedExceptions(e);
		}

	}

}
