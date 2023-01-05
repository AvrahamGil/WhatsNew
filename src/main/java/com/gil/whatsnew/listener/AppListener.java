package com.gil.whatsnew.listener;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.ArticleLogic;


@Service
public class AppListener implements ApplicationListener<AppEvent> {

	@Autowired
	private ArticleLogic articleLogic;

	private final String[] categories = { "news", "business", "sport", "technology", "travel" };

	@Override
	public void onApplicationEvent(AppEvent event) {
		List<String> source;
		
		Set<Article> articles = new HashSet<Article>();

		Set<NewYorkTimesApi> newYorkArticles = new HashSet<NewYorkTimesApi>();

		boolean articlesDeleted = false;
		
		try {
			for (String category : categories) {
				source = new ArrayList<String>();

				source = articleLogic.addingSources(source, category);

				if(!articlesDeleted) {
					articleLogic.deleteArticles(source);
				}
				
				articles = articleLogic.getApiArticles(source);
				
 				newYorkArticles = category.equals("news") || category.equals("sport") ? articleLogic.getNewYorkApiArticles(category) : null;
				
				if(newYorkArticles != null) articleLogic.addNewYorkTimesArticles(newYorkArticles, category);
			
				articleLogic.addArticlesToStock(articles, category);
			}

		} catch (ApplicationException e) {
			ExceptionHandler.generatedExceptions(e);
		}

	}

}
