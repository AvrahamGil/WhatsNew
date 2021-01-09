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
import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.Multimedia;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.dao.NewsDao;
import com.gil.whatsnew.enums.NewsType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@Service
public class NewsLogic{
	
	@Autowired
	private BaseRequest request;
	
	@Autowired
	private NewsDao newsDao;
	
	private List<NewYorkTimesApi> newYorkTimesArticles = new ArrayList<NewYorkTimesApi>();
	private int counter = 1;
	private int maxCounter = 7;
	
	public void addArticlesToStock(Set<ContextApi>articles) {
		try {
			if(!articles.isEmpty()) {
				for(ContextApi article : articles) {
					newsDao.addArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addNewYorkTimesArticles(Set<NewYorkTimesApi>newYorkTimesArticles) {
		try {
			if(!newYorkTimesArticles.isEmpty()) {
				for(NewYorkTimesApi article : newYorkTimesArticles) {
					newsDao.addNewYorkArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public ContextApi getRandomArticles() throws ApplicationException {
		ContextApi article = new ContextApi();
		
		article = newsDao.getRandomArticle();

		if(article == null)
			return null;
		
		return article;	
	}
	
	public List<ContextApi> getListOfAllNewsArticles() throws ApplicationException {
		List<ContextApi>articles = new ArrayList<ContextApi>(); 
		
		articles= newsDao.getAllArticles();
	
		if(articles == null)
			return null;
		
		return articles;	
	}
	
	public List<NewYorkTimesApi> getListOfNewYorkTimesNewsArticles() throws ApplicationException  {
		List<NewsType> types = new ArrayList<NewsType>();
		try {
			types.add(NewsType.NewYorkTimes);

			for (NewsType type : types) {
				newYorkTimesArticles = newsDao.getNewYorkTimesArticles(type.toString());

				if (newYorkTimesArticles.size() != 0) {
					types.clear();
					return newYorkTimesArticles;
				}
			}

			if (newYorkTimesArticles == null)
				return null;

			
			return newYorkTimesArticles;
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		} finally {
			types.clear();
		}
		
		
		return newYorkTimesArticles;
	}
	
	public void deleteArticles() throws ApplicationException {
		try {
			newsDao.deleteArticle();
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public List<ContextApi> getArticles(NewsType newsType) throws ClientProtocolException, IOException {
		List<ContextApi>articles = new ArrayList<ContextApi>();
		HttpResponse response = request.getNewsArticleByType(newsType.getSite());
		List<ContextApi>tempArticles = generatedArticleList(response);
		String lastTitle = "";
		String lastUrl = "";
		
		for(ContextApi article : tempArticles) {
			if(article.getUrl().contentEquals(newsType.getSite()) || counter <= maxCounter || article.getImage().getWebpageUrl() != null && !lastTitle.equals(article.getTitle()) && !lastUrl.equals(article.getUrl())) {
				
				article.setImageUrl(article.getImage().getUrl());
				article.setNewsType(newsType.toString());
				articles.add(article);
				counter++;
				lastTitle = article.getTitle();
				lastUrl = article.getUrl();
			}
			
			if (counter == maxCounter) {
				counter = 0;
				return articles;
			}
			
			if(articles.size() == maxCounter)
				break;
		}
		
		return articles;
	}
	
	public List<NewYorkTimesApi> getNewYorkArticles() throws ClientProtocolException, IOException {
		String category = "politics";
		HttpResponse response = request.getNewYorkBusinessArticleByType(category);
		JSONArray items = stringToJson(response).getJSONArray("results");
		Multimedia[] multimedia = new Multimedia[items.length()];

		 for(int counter=0; counter < 4; counter++) {
			 if(items.getJSONObject(counter).get("url").toString().contains("politics")) {
					NewYorkTimesApi otherArticle = new NewYorkTimesApi();
				 	multimedia[counter] = new Multimedia();
				 	otherArticle.setTitle(items.getJSONObject(counter).get("title").toString());
				 	otherArticle.setSection(items.getJSONObject(counter).get("section").toString());
					otherArticle.setDescription(items.getJSONObject(counter).get("abstract").toString());
				 	otherArticle.setUrl(items.getJSONObject(counter).get("url").toString());
				 	
				 	multimedia[counter].setUrl(items.getJSONObject(counter).getJSONArray("multimedia").getJSONObject(counter).get("url").toString());
				 	otherArticle.setUrlToImage(multimedia[counter].getUrl());
				 	newYorkTimesArticles.add(otherArticle);
			 }
			 
	        }

		return newYorkTimesArticles;
	}
	
	private List<ContextApi>generatedArticleList(HttpResponse response) throws ParseException, IOException {
		//Library
		Gson gson = new Gson();
		
		//Get response content
		HttpEntity entity = response.getEntity();
		
		//Take the response and make it to be string
		String responseString = EntityUtils.toString(entity);
		
		//Change the string to JSON object
		JSONObject json = new JSONObject(responseString);
		
		//Looking the json array in the response using key
		//(key must be inside the respose)
		JSONArray items = json.getJSONArray("value");
		
		//Make the list to be List<Article>
		TypeToken<List<ContextApi>> token = new TypeToken<List<ContextApi>>(){};
		
		//Json object to List<Article>
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
	
	public void addingTypesSites(List<NewsType>recieveTypes) {
		if(recieveTypes.size() == 0) {
			recieveTypes.add(NewsType.Theguardian);
			recieveTypes.add(NewsType.Ynetnews);
			recieveTypes.add(NewsType.WallStreetJournal);
			recieveTypes.add(NewsType.Time);
			recieveTypes.add(NewsType.ABCnews);
			recieveTypes.add(NewsType.CNN);
			recieveTypes.add(NewsType.BBCnews);
			recieveTypes.add(NewsType.Covid19);
			recieveTypes.add(NewsType.Theblaze);
			recieveTypes.add(NewsType.NBC);
			recieveTypes.add(NewsType.JerusalemNews);
		}
	}
}
