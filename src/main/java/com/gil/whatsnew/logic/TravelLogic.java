package com.gil.whatsnew.logic;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.dao.TravelDao;
import com.gil.whatsnew.enums.TravelType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class TravelLogic {
	@Autowired
	private BaseRequest request;

	@Autowired
	private TravelDao travelDao;
	
	private Gson gson = new Gson();
	private int counter = 1;
	private int maxCounter = 7;

	
	public void addArticlesToStock(Set<ContextApi>articles) {
		try {
			if(!articles.isEmpty()) {
				for(ContextApi article : articles) {
					travelDao.addArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	public ContextApi getRandomArticles() throws ApplicationException {
		ContextApi article = new ContextApi();
		article = travelDao.getRandomArticle();
	
		if(article == null)
			return null;
		
		return article;	
	}
	
	public List<ContextApi> getListOfAllNewsArticles() throws ApplicationException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		try {
			articles = new ArrayList<ContextApi>();

			articles = travelDao.getAllArticles();

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
			travelDao.deleteArticle();
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public List<ContextApi> getArticles(TravelType travelType) throws ClientProtocolException, IOException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		HttpResponse response = request.getNewsArticleByType(travelType.getSite());
		List<ContextApi> tempArticles = generatedArticleList(response);
		String lastTitle = "";
		String lastUrl = "";
		
		for (ContextApi article : tempArticles) {
			if (article.getUrl().contains(travelType.getSite()) && counter <= maxCounter
					&& article.getImage().getWebpageUrl() != null && !lastTitle.equals(article.getTitle()) && !lastUrl.equals(article.getUrl())) {

				article.setImageUrl(article.getImage().getUrl());
				article.setNewsType(travelType.toString());

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


	private JSONObject stringToJson(HttpResponse response) throws ParseException, IOException {
		// Get response content
		HttpEntity entity = response.getEntity();

		// Take the response and make it to be string
		String responseString = EntityUtils.toString(entity);

		// Change the string to JSON object
		JSONObject json = new JSONObject(responseString);

		return json;
	}

	public void addingTypesSites(List<TravelType> recieveTypes) {
		recieveTypes.clear();

		recieveTypes.add(TravelType.Businesstravelnews);
		recieveTypes.add(TravelType.Eturbonews);
		recieveTypes.add(TravelType.Phocuswire);
		recieveTypes.add(TravelType.Skift);
		recieveTypes.add(TravelType.Travelandtourworld);
		recieveTypes.add(TravelType.Traveldailynews);
		recieveTypes.add(TravelType.Travelpulse);
		recieveTypes.add(TravelType.TravelWeekly);
		recieveTypes.add(TravelType.Ttgmedia);
		recieveTypes.add(TravelType.Ttnworldwide);
		recieveTypes.add(TravelType.Visitseattle);
		recieveTypes.add(TravelType.LonelyPlanet);
	}
}
