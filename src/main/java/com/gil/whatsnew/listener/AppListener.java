package com.gil.whatsnew.listener;


import java.util.*;

import com.gil.whatsnew.bean.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.ArticleLogic;


@Component
public class AppListener implements ApplicationListener<AppEvent> {

	@Autowired
	private ArticleLogic articleLogic;

	private final String[] sharedCategories = {"business","sports", "technology", "entertainment","tourism" };

	private final String[] differentCategories = {"general","politics","world"};


	@Override
	public void onApplicationEvent(AppEvent event) {
		System.out.println("Getting categories ready to extracting articles");

		Set<Article> breakingNewsArticles = null;
		final String breakingNewsCategory = "breaking-news";

		Set<Article> sameArticles = null;
		Set<Article> diffArticles = null;

		boolean articlesDeleted = false;
		
		try {
			List<String>breakingNewsCategories = new ArrayList<>();
			breakingNewsCategories.add(breakingNewsCategory);

			List<String>sameCategoriesArticles;
			List<String>diffCategoriesArticles;

			sameCategoriesArticles = Arrays.asList(this.sharedCategories);
			diffCategoriesArticles = Arrays.asList(this.differentCategories);

			if(!articlesDeleted) {
				articleLogic.deleteArticles(breakingNewsCategories);
				articleLogic.deleteArticles(sameCategoriesArticles);
				articleLogic.deleteArticles(diffCategoriesArticles);
			}

			breakingNewsArticles = articleLogic.getArticlesApi(breakingNewsCategory, false);
			articleLogic.addArticlesToStock(breakingNewsArticles, breakingNewsCategory);


			for(String category: sameCategoriesArticles) {
				sameArticles = articleLogic.getArticlesApi(category, true);
				articleLogic.addArticlesToStock(sameArticles, category);
			}

			for(String category: diffCategoriesArticles) {
				diffArticles = articleLogic.getArticlesApi(category, false);
				articleLogic.addArticlesToStock(diffArticles, category);
			}


			if(sameArticles != null) sameArticles.clear();
			if(diffArticles != null) diffArticles.clear();


		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

}
