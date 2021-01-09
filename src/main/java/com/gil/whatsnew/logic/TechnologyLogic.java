package com.gil.whatsnew.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.CatcherApi;
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.dao.TechnologyDao;
import com.gil.whatsnew.enums.TechnologyType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class TechnologyLogic {
	
	@Autowired
	private BaseRequest request;

	@Autowired
	private TechnologyDao technologyDao;
	
	@Autowired
	private ContextApi article;
	
	private Gson gson = new Gson();
	private int counter = 1;
	private int maxCounter = 7;

	
	public void addArticlesToStock(Set<ContextApi>articles) {
		try {
			if(!articles.isEmpty()) {
				for(ContextApi article : articles) {
					technologyDao.addArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addCatcherArticles(Set<CatcherApi>catcherArticles) {
		try {
			if(!catcherArticles.isEmpty()) {
				for(CatcherApi article : catcherArticles) {
					technologyDao.addOtherArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public ContextApi getRandomArticles() throws ApplicationException {
		ContextApi article = new ContextApi();
		 article = technologyDao.getRandomArticle();
	
		if(article == null)
			return null;
		
		return article;	
	}
	
	public List<ContextApi> getListOfAllNewsArticles() throws ApplicationException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		try {
			articles = new ArrayList<ContextApi>();

			articles = technologyDao.getAllArticles();

			if (articles == null)
				return null;

			return articles;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return articles;
	}

	public List<CatcherApi> getListOfOtherNewsArticles() throws ApplicationException {
		List<CatcherApi> articles = new ArrayList<CatcherApi>();
		try {
			articles = new ArrayList<CatcherApi>();

			articles = technologyDao.getOtherArticles();

			if (articles == null)
				return null;


			return articles;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return articles;
	}

	public void deleteArticles() throws ApplicationException {
		try {
			technologyDao.deleteArticle();
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public List<ContextApi> getArticles(TechnologyType technologyType) throws ClientProtocolException, IOException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		HttpResponse response = request.getNewsArticleByType(technologyType.getSite());
		List<ContextApi> tempArticles = generatedArticleList(response);
		String lastTitle = "";
		String lastUrl = "";
		
		for (ContextApi article : tempArticles) {
			if (article.getUrl().contains(technologyType.getSite()) && counter <= maxCounter && article.getImage().getWebpageUrl() != null && !lastTitle.equals(article.getTitle()) && !lastUrl.equals(article.getUrl())) {

				article.setImageUrl(article.getImage().getUrl());
				article.setNewsType(technologyType.toString());

				if (article.getNewsType() != null)
					articles.add(article);

				counter++;
				lastTitle = article.getTitle();
				lastUrl = article.getUrl();
			}

			if (counter == maxCounter) {
				counter = 0;
				return articles;
			}

			if (articles.size() == maxCounter)
				break;
		}

		return articles;
	}

	public List<CatcherApi> getOtherArticles(TechnologyType technologyType) throws ClientProtocolException, IOException {
		List<CatcherApi> articles = new ArrayList<CatcherApi>();
		HttpResponse response = request.getOtherNewsByType(technologyType.getSite());
		List<CatcherApi> tempArticles = generatedOtherArticleList(response);
		String lastTitle = "";
		String lastUrl = "";
		
		
		for (CatcherApi article : tempArticles) {
				if (article.getLink().contains(technologyType.getSite()) && counter <= maxCounter && article.getMedia() != null && !lastTitle.equals(article.getTitle()) && !lastUrl.equals(article.getLink())) {

					article.setNewsType(technologyType.toString());

					if (article.getNewsType() != null)
						articles.add(article);

					counter++;
					lastTitle = article.getTitle();
					lastUrl = article.getLink();
				}

				if (counter == maxCounter)
					return articles;

				if (articles.size() == maxCounter)
					break;
		
		}

		return articles;
	}

	

	private List<ContextApi> generatedArticleList(HttpResponse response) throws ParseException, IOException {
		// Looking the json array in the response using key
		// (key must be inside the respose)
		JSONArray items = stringToJson(response).getJSONArray("value");

		// Make the list to be List<Article>
		TypeToken<List<ContextApi>> token = new TypeToken<List<ContextApi>>() {
		};

		// Json object to List<Article>
		List<ContextApi> tempArticles = gson.fromJson(items.toString(), token.getType());

		return tempArticles;
	}

	private List<CatcherApi> generatedOtherArticleList(HttpResponse response) throws ParseException, IOException {
		// Looking the json array in the response using key
		// (key must be inside the respose)
		JSONArray items = stringToJson(response).getJSONArray("articles");

		// Make the list to be List<Article>
		TypeToken<List<CatcherApi>> token = new TypeToken<List<CatcherApi>>() {
		};

		// Json object to List<Article>
		List<CatcherApi> tempArticles = gson.fromJson(items.toString(), token.getType());

		return tempArticles;
	}
	
	private JSONObject stringToJson(HttpResponse response) throws ParseException, IOException {
		// Get response content
		HttpEntity entity = response.getEntity();

		// Take the response and make it to be string
		String responseString = EntityUtils.toString(entity);

		// Change the string to JSON object
		JSONObject json = new JSONObject(responseString);

		return json;
	}

	public void addingTypesSites(List<TechnologyType> recieveTypes) {
		recieveTypes.clear();
		
		recieveTypes.add(TechnologyType.Arstechnica);
		recieveTypes.add(TechnologyType.Axios);
		recieveTypes.add(TechnologyType.Engadget);
		recieveTypes.add(TechnologyType.Gizmodo);
		recieveTypes.add(TechnologyType.Sciencedaily);
		recieveTypes.add(TechnologyType.Techcrunch);
		recieveTypes.add(TechnologyType.Thenextweb);
		recieveTypes.add(TechnologyType.Theverge);
		recieveTypes.add(TechnologyType.Wired);
	
	}
}
