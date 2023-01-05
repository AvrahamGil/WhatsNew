package com.gil.whatsnew.logic;

import java.io.IOException;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.Multimedia;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.dao.ArticleDaoMongo;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.StringPaths;
import com.gil.whatsnew.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ArticleLogic {

	@Autowired
	private ArticleDaoMongo articleDaoMongo;

	@Autowired
	private BaseRequest request;

	private final String[] categories = { "news", "business", "sport", "technology", "travel" };

	private final String[] newYorkTimes = { "newsNewYork", "sportsNewYork" };

	private final String path = "domains";
	private final int maxDomains = 12;

	private int counter = 1;
	private int maxCounter = 7;

	public void addArticlesToStock(Set<Article> jsonArticles, String category) throws ApplicationException {
		try {
			if (!jsonArticles.isEmpty()) {
				for (Article article : jsonArticles) {
					articleDaoMongo.addArticle(article, null, category);
				}
			}
		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

	}

	public void addNewYorkTimesArticles(Set<NewYorkTimesApi> newYorkTimesArticles, String category)
			throws ApplicationException {
		try {
			if (!newYorkTimesArticles.isEmpty()) {
				for (NewYorkTimesApi article : newYorkTimesArticles) {
					articleDaoMongo.addArticle(null, article, category);
				}
			}
		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}

	public List<Article> getRandomArticles() throws ApplicationException {
		List<Article> articles = new ArrayList<Article>();
		
		List<Article>random = new ArrayList<Article>();
		
		try {
			for (String category : categories) {
				articles = articleDaoMongo.getRandomArticle(category);
				
				for(Article article : articles) {
					random.add(article);
				}
			}
			
			if (random.isEmpty())
				return null;

			return random;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

		return null;
	}

	public List<List<Article>> getListOfNewsArticles() throws ApplicationException {
		List<List<Article>> articles = new ArrayList<List<Article>>();

		try {
			for (String category : categories) {
				articles.add(articleDaoMongo.getAllArticles(category));
			}

			if (articles.isEmpty())
				return null;

			return articles;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return articles;
	}

	public List<NewYorkTimesApi> getListOfNewYorkTimesArticles() throws ApplicationException {
		List<NewYorkTimesApi> newYorkTimesArticles = new ArrayList<NewYorkTimesApi>();

		try {
			for (String category : newYorkTimes) {
				List<NewYorkTimesApi> articles = articleDaoMongo.getNewYorkTimesArticles(category, "NewYorkTimes");
				
				for(NewYorkTimesApi article : articles) {
					if(article.getMultimedia() != null) {
						newYorkTimesArticles.add(article);
					}
				}
				
				if (newYorkTimesArticles.size() != 0) {
					return newYorkTimesArticles;
				}
			}

			if (newYorkTimesArticles.isEmpty())
				return null;

			return newYorkTimesArticles;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		} 
		return null;
	}

	public void deleteArticles(List<String> types) throws ApplicationException {
		boolean isEmpty = false;
		
		try {
			isEmpty = types.isEmpty() || getListOfNewsArticles().isEmpty() ? true : false;
			
			if(isEmpty) throw new ApplicationException(ErrorType.Domains_Failed,ErrorType.Domains_Failed.getMessage(),false); 
							
			for(String type : types) {
				articleDaoMongo.deleteArticle(type);
			}
			
		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}

	public Set<Article> getApiArticles(List<String> types) throws ApplicationException {
		List<Article> articles = new ArrayList<Article>();
		Set<Article> uniqueArticles = new HashSet<Article>();

		try {
			for (String type : types) {
 				articles = getApiArticles(type);

				for (Article article : articles) {
					if (!uniqueArticles.contains(article)) {
						uniqueArticles.add(article);
					}
				}
			}

			return uniqueArticles;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

		return uniqueArticles;
	}

	public List<Article> getApiArticles(String type) throws ApplicationException {
		List<Article> articles = new ArrayList<Article>();

		try {
			HttpResponse response = request.getNewsArticles(type);
			List<Article> tempArticles = generatedArticleList(response);
			String lastTitle = "";
			String lastUrl = "";

			for (Article article : tempArticles) {
				if (article.getUrl().contentEquals(type) || counter <= maxCounter
						|| article.getImage().getWebpageUrl() != null && !lastTitle.equals(article.getTitle())
								&& !lastUrl.equals(article.getUrl())) {

					article.setImageUrl(article.getImage().getUrl());
					article.setNewsType(type);
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
		} catch (ApplicationException e) {
			throw new ApplicationException(ErrorType.Api_Failed,ErrorType.Api_Failed.getMessage(), false);
		}

	}

	public Set<NewYorkTimesApi> getNewYorkApiArticles(String type) throws ApplicationException {
		NewYorkTimesApi otherArticle = null;
		
		try {
			HttpResponse response = request.getNewYorkTimesArticles(type);
			JSONArray items = (JSONArray) JsonUtils.stringToJson(response);
			Multimedia[] multimedia = new Multimedia[items.length()];

			Set<NewYorkTimesApi> newYorkTimesArticles = new HashSet<NewYorkTimesApi>();

			for (int counter = 0; counter < items.length(); counter++) {
				if(items.getJSONObject(counter).get("web_url").toString().contains(type)) {
				
				otherArticle = new NewYorkTimesApi();
				multimedia[counter] = new Multimedia();
				
				otherArticle.setId(UUID.randomUUID().toString());
				otherArticle.setTitle(items.getJSONObject(counter).get("abstract").toString());
				otherArticle.setDescription(items.getJSONObject(counter).get("lead_paragraph").toString());
				otherArticle.setUrl(items.getJSONObject(counter).get("web_url").toString());
				
				multimedia[counter].setUrl("https://www.nytimes.com/" + items.getJSONObject(counter).getJSONArray("multimedia")
							.getJSONObject(counter).get("url").toString());
				multimedia[counter].setHeight( items.getJSONObject(counter).getJSONArray("multimedia")
						.getJSONObject(counter).get("height").toString());
				multimedia[counter].setWidth( items.getJSONObject(counter).getJSONArray("multimedia")
						.getJSONObject(counter).get("width").toString());
				
				otherArticle.setMultimedia(multimedia[counter]);
				
				newYorkTimesArticles.add(otherArticle);
				}
			}

			return newYorkTimesArticles;
			
		} catch (ParseException | IOException | JSONException | org.json.simple.parser.ParseException  e) {
			throw new ApplicationException(ErrorType.Api_Failed,ErrorType.Api_Failed.getMessage(), false);
		}

	}
	public List<String> addingSources(List<String> types, String category) throws ApplicationException {
		String site = "";

		if (types.size() == 0) {
			try {
				for (int i = 1; i <= maxDomains; i++) {
					String stringI = String.valueOf(i);
					
					site = JsonUtils.readJsonFile(category, stringI, StringPaths.getPath(path));
					
					if (site != "")
						types.add(site);
				}

				return types;

			} catch (ApplicationException e) {
				throw new ApplicationException(ErrorType.Domains_Failed, ErrorType.Domains_Failed.getMessage(), false);
			}
		}
		return types;
	}
	private List<Article> generatedArticleList(HttpResponse response) throws ApplicationException {

		Gson gson = new Gson();

		try {

			HttpEntity entity = response.getEntity();

			String responseString = EntityUtils.toString(entity);

			JSONObject json = new JSONObject(responseString);

			JSONArray items = json.getJSONArray("value");

			TypeToken<List<Article>> token = new TypeToken<List<Article>>() {
			};

			List<Article> tempArticles = gson.fromJson(items.toString(), token.getType());

			return tempArticles;

		} catch (ParseException | IOException e) {
			throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
		}

	}
}
