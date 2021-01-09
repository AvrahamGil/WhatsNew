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
import com.gil.whatsnew.dao.SportDao;
import com.gil.whatsnew.enums.SportType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class SportLogic {
	
	@Autowired
	private BaseRequest request;

	@Autowired
	private SportDao sportDao;
 
	
	private List<NewYorkTimesApi> newYorkTimesArticles = new ArrayList<NewYorkTimesApi>();
	private Gson gson = new Gson();
	private int counter = 1;
	private int maxCounter = 7;

	
	public void addArticlesToStock(Set<ContextApi>articles) {
		try {
			if(!articles.isEmpty()) {
				for(ContextApi article : articles) {
					sportDao.addArticle(article);
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
					sportDao.addNewYorkArticle(article);
				}
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public ContextApi getRandomArticles() throws ApplicationException {
		ContextApi article = new ContextApi();
		article = sportDao.getRandomArticle();
	
		if(article == null)
			return null;
		
		return article;	
	}
	
	public List<ContextApi> getListOfAllNewsArticles() throws ApplicationException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		try {
			articles = new ArrayList<ContextApi>();

			articles = sportDao.getAllArticles();

			if (articles == null)
				return null;

			return articles;
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return articles;
	}


	public List<NewYorkTimesApi> getListOfNewYorkTimesNewsArticles() throws ApplicationException {
		List<SportType> types = new ArrayList<SportType>();
		try {
			types.add(SportType.NewYorkTimes);

			for (SportType type : types) {
				newYorkTimesArticles = sportDao.getNewYorkTimesArticles(type.toString());

				if (newYorkTimesArticles.size() != 0) {
					types.clear();
					return newYorkTimesArticles;
				}

				newYorkTimesArticles.clear();

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
			sportDao.deleteArticle();
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}
	
	public List<ContextApi> getArticles(SportType sportType) throws ClientProtocolException, IOException {
		List<ContextApi> articles = new ArrayList<ContextApi>();
		HttpResponse response = request.getNewsArticleByType(sportType.getSite());
		List<ContextApi> tempArticles = generatedArticleList(response);
		String lastTitle = "";
		String lastUrl = "";
		
		for (ContextApi article : tempArticles) {
			if (article.getUrl().contains(sportType.getSite()) && counter <= maxCounter && article.getImage().getWebpageUrl() != null && !lastTitle.equals(article.getTitle()) && !lastUrl.equals(article.getUrl())) {

				article.setImageUrl(article.getImage().getUrl());
				article.setNewsType(sportType.toString());

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

	
	public List<NewYorkTimesApi> getNewYorkArticles() throws ClientProtocolException, IOException {
		String category = "sports";
		HttpResponse response = request.getNewYorkBusinessArticleByType(category);
		JSONArray items = stringToJson(response).getJSONArray("results");
		Multimedia[] multimedia = new Multimedia[items.length()];

		 for(int counter=0; counter < 4; counter++) {
			 if(items.getJSONObject(counter).get("url").toString().contains("sports")) {
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

	public void addingTypesSites(List<SportType> recieveTypes) {
		recieveTypes.clear();
		
		recieveTypes.add(SportType.SportsIllustrated);
		recieveTypes.add(SportType.Cbssports);
		recieveTypes.add(SportType.DeadSpin);
		recieveTypes.add(SportType.ESPN);
		recieveTypes.add(SportType.MLB);
		recieveTypes.add(SportType.Syracuse);
		recieveTypes.add(SportType.YardBarker);
		recieveTypes.add(SportType.Sbnation);
		recieveTypes.add(SportType.Sportingnews);
		recieveTypes.add(SportType.Yahoo);
		recieveTypes.add(SportType.Fansided);
	}

}
