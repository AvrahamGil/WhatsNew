package com.gil.whatsnew.logic;

import java.io.IOException;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.gil.whatsnew.bean.*;
import com.gil.whatsnew.enums.RequestsUrl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.dao.ArticleDao;
import com.gil.whatsnew.enums.Cookies;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.StringPaths;
import com.gil.whatsnew.utils.Authentication;
import com.gil.whatsnew.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ArticleLogic {

	@Autowired
	private ArticleDao articleDaoMongo;

	@Autowired
	private BaseRequest request;

	@Autowired
	private Authentication authentication;

	private final String[] categories = {"politics", "general", "business", "sports", "technology", "entertainment", "world"};
	private final String specialChar = "~`!#$%^&*(){}[]+-_=/><:\"\\?;";

	private final String path = "domains";
	private final int maxDomains = 13;

	private int counter = 1;
	private int maxCounter = 16;

	public void addArticlesToStock(Set<Article> jsonArticles, String category) throws ApplicationException {
		try {
			if (!jsonArticles.isEmpty()) {
				for (Article article : jsonArticles) {
					if(article.getArticleId() == null)
						article.setArticleId(UUID.randomUUID().toString());
					articleDaoMongo.addArticle(article, category);
				}
			}
		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

	}

	public List<List<Article>> getListOfNewsArticles(HttpServletRequest request) throws ApplicationException {
		List<List<Article>> articles = new ArrayList<List<Article>>();


		try {
			for (String category : categories) {
				articles.add(articleDaoMongo.getAllArticles(category));
			}

			if (request != null && request.getCookies() != null) {
				List<List<Article>> likedArticles = getArticlesLiked(request, articles);


				likedArticles = likedArticles != null ? likedArticles : null;


				likedArticles = likedArticles != null ? likedArticles : null;

				if (likedArticles != null) return likedArticles;
			}

			return articles;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return articles;
	}


	public void deleteArticles(List<String> types) throws ApplicationException {
		boolean isEmpty = false;

		try {
			isEmpty = types.isEmpty() || getListOfNewsArticles(null).isEmpty() ? true : false;

			if (isEmpty)
				throw new ApplicationException(ErrorType.Domains_Failed, ErrorType.Domains_Failed.getMessage(), false);

			for (String type : types) {
				articleDaoMongo.deleteArticle(type);
			}

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
	}

	public Set<Article> getArticlesApi(String category, boolean sameCategories) throws ApplicationException {
		Set<Article> newsApiDataList = new HashSet<>();

		try {
			newsApiDataList = initNewsResponse(category, sameCategories);
			return newsApiDataList;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}

		return newsApiDataList;
	}

	public Set<Article> initNewsResponse(String category, boolean sameCategories) throws ApplicationException {
		List<HttpResponse>response;
		Set<Article> articles = new HashSet<>();
		Set<Article> newsArticleData = new HashSet<>();
		Set<Set<Article>>getResultsData = new HashSet<>();

		try {
			if(sameCategories) {
				for(int i=0; i < 2; i++) {
					RequestsUrl requestUrl = i == 0 ? RequestsUrl.NewsDataAPI : RequestsUrl.GNewsAPI;
					String key = requestUrl == RequestsUrl.NewsDataAPI ? "results" : "articles";
					response = request.getNewsArticles(category, requestUrl);
					newsArticleData = generatedArticlesFromResponse(response, key, category);

					getResultsData.add(newsArticleData);

				}

            } else {
					if(!category.equalsIgnoreCase("breaking-news")) {
						RequestsUrl requestUrl = category.equalsIgnoreCase("politics") ||  category.equalsIgnoreCase("world") ?
								RequestsUrl.NewsDataAPI  : RequestsUrl.GNewsAPI;
						String key = requestUrl == RequestsUrl.NewsDataAPI ? "results" : "articles";
						response = request.getNewsArticles(category, requestUrl);
						newsArticleData = generatedArticlesFromResponse(response, key, category);

                    } else {
						response = request.getNewsArticles("", RequestsUrl.CurrentsApi);
						newsArticleData = generatedArticlesFromResponse(response, "news", category);
                    }

                getResultsData.add(newsArticleData);
            }

            articles = convertArticlesData(getResultsData,category);
            return articles;

        } catch (ApplicationException | IOException e) {
			throw new ApplicationException(ErrorType.Api_Failed, ErrorType.Api_Failed.getMessage(), false);
		}
    }

	private Set<Article>convertArticlesData(Set<Set<Article>>articlesData,String category) {
		Set<Article> articles = new HashSet<>();

		String lastTitle = "";
		String lastUrl = "";

		for(Set<Article>data : articlesData) {
			for (Article articleData : data) {
				if (articleData.getUrl().contentEquals(category) || counter <= maxCounter
						|| !articleData.getImageUrl().isEmpty() && !lastTitle.equals(articleData.getTitle())
						&& !lastUrl.equals(articleData.getUrl())) {

					articleData.setCategory(category);  // Use the articleData directly
					articles.add(articleData);  // Add directly to the set

					counter++;
					lastTitle = articleData.getTitle();
					lastUrl = articleData.getUrl();
				}

				if (counter == maxCounter) {
					counter = 0;
					return articles;
				}

			}
		}

		return articles;
	}

	public ResponseEntity<Object> addIntoFavorit(String articleId, HttpServletRequest request) throws ApplicationException {
		UserArticles details = null;
		String randomId = UUID.randomUUID().toString();
		boolean verify = true;

		String[] userDetails = new String[2];

		try {
			if (articleId == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			long id = Long.valueOf(articleId);

			if (id < 10000)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			Article articles = articleDaoMongo.getArticleDetails(articleId);

			if (articles == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			details = new UserArticles();

			details.setId(randomId);
			details.setTitle(articles.getTitle());
			details.setType(articles.getCategory());

			Cookie[] cookies = request.getCookies();

			if (cookies == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Cookies.XTOKEN.getName())) {

					userDetails = cookie.getValue() != null ? authentication.verifyJwtToken(cookie.getValue()) : null;

				}

				if (cookie.getName().equals(Cookies.XCSRFTOKEN.getName())) {
					verify = (cookie.getValue() != null) && authentication.verifyCSRFToken(request) ? true : false;

				}
			}

			if (!verify || userDetails == null)
				return null;

			boolean liked = articleDaoMongo.isArticleFavorated(articles.getTitle(), userDetails[1]);

			if (liked) {
				articleDaoMongo.deleteFavoritArticle(details);
			} else {
				articleDaoMongo.addFavoritArticle(details);
			}

			ResponseEntity<Object> res = new ResponseEntity<Object>(HttpStatus.OK);

			return res;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;

	}

	public List<Article> getFavoritArticles(HttpServletRequest request) throws ApplicationException {
		List<UserArticles> details;
		List<Article> articles = new ArrayList<Article>();
		String[] userDetails = new String[2];

		try {
			Cookie[] cookies = request.getCookies();

			if (cookies == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Cookies.XTOKEN.getName())) {

					userDetails = cookie.getValue() != null ? authentication.verifyJwtToken(cookie.getValue()) : null;
				}
			}

			if (userDetails == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);

			details = articleDaoMongo.getFavoritArticles(userDetails[1]);

			for (UserArticles article : details) {
				articles.add(articleDaoMongo.getArticleDetails(article.getTitle()));
			}

			if (articles.isEmpty())
				return null;

			return articles;

		} catch (ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
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

	private List<Article>generateNewsDataApiArticlesList(List<HttpResponse> responses, String key) throws ApplicationException {
		Gson gson = new Gson();
		List<Article> tempNewsDataApiArticles = new ArrayList<>();
		Type listType = null;

		if(!key.equals("results")) return null;

		for (HttpResponse response : responses) {
			HttpEntity entity = response.getEntity();
			try {
				String responseString = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(responseString);

				JSONArray items = json.getJSONArray(key);

				// DEBUGGING: Log the raw JSON to see its structure
				System.out.println("Raw JSON Array: " + items.toString());

				String itemsString = items.toString();


				listType = new TypeToken<List<Article>>() {}.getType();
				List<Article> articles = gson.fromJson(itemsString, listType);
				tempNewsDataApiArticles.addAll(articles);
				return tempNewsDataApiArticles;

			} catch (Exception e) {
				throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
			}
		}
		return null;
	}

	private Set<Article>generatedArticlesFromResponse(List<HttpResponse> responses, String key, String category) throws ApplicationException {
		Gson gson = new Gson();
		Set<Article>articles = new HashSet<>();
		List<GNewsApiStructure> tempGNewsApiArticles = new ArrayList<>();
		List<NewsApiDataStructure> tempNewsDataApiArticles = new ArrayList<>();
		List<NewsArticleStructure> newsApiArticles = new ArrayList<>();

		Type listType;
		JSONArray items;
		Article article;

		for(HttpResponse response : responses) {
			HttpEntity entity = response.getEntity();
			try {
				String responseString = EntityUtils.toString(entity);
				JSONObject json = new JSONObject(responseString);

				if(key.equals("articles")) {
					items = json.getJSONArray(key);
					String itemsString = items.toString();
					listType = new TypeToken<List<GNewsApiStructure>>() {}.getType();
					List<GNewsApiStructure> gNewsArticleData = gson.fromJson(itemsString, listType);
					tempGNewsApiArticles.addAll(gNewsArticleData);

					for(GNewsApiStructure apiData : tempGNewsApiArticles) {
						article = new Article();
						article.setCategory(category);
						article.setUrl(apiData.getUrl());
						article.setTitle(apiData.getTitle());
						article.setContent(apiData.getContent());
						article.setDescription(apiData.getDescription());
						article.setImageUrl(apiData.getImage());

						articles.add(article);
					}

				} else if(key.equals("results")) {
					items = json.getJSONArray(key);
					String itemsString = items.toString();
					listType = new TypeToken<List<NewsApiDataStructure>>() {}.getType();
					List<NewsApiDataStructure> newsApiDataStructureList = gson.fromJson(itemsString, listType);
					tempNewsDataApiArticles.addAll(newsApiDataStructureList);

					for(NewsApiDataStructure apiData : tempNewsDataApiArticles) {
						article = new Article();
						article.setCategory(category);
						article.setUrl(apiData.getLink());
						article.setTitle(apiData.getTitle());
						article.setContent(apiData.getContent());
						article.setDescription(apiData.getDescription());
						article.setImageUrl(apiData.getImage_url());

						articles.add(article);
					}
				} else if(key.equals("news")) {
					items = json.getJSONArray(key);
					String itemsString = items.toString();
					listType = new TypeToken<List<NewsArticleStructure>>() {}.getType();
					List<NewsArticleStructure> breakingNewsApiArticles = gson.fromJson(itemsString, listType);
					newsApiArticles.addAll(breakingNewsApiArticles);

					for(NewsArticleStructure apiData : newsApiArticles) {
						article = new Article();
						article.setCategory(category);
						article.setUrl(apiData.getUrl());
						article.setTitle(apiData.getTitle());
						article.setContent("");
						article.setDescription(apiData.getDescription());
						article.setImageUrl(apiData.getImage());

						articles.add(article);
					}
				}

				return articles;

				} catch (Exception e) {
					throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
				}
			}
		return null;
	}


	private List<List<Article>> getArticlesLiked(HttpServletRequest request, List<List<Article>>articles) throws ApplicationException {
		List<UserArticles> userArticles = new ArrayList<UserArticles>();
		Map<String,Integer>types = new HashMap<String,Integer>();

			try {
				if(request == null) return null;

				String[] userDetails = new String[2];
				Cookie[] cookies = request.getCookies();

				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(Cookies.XTOKEN.getName())) {

						userDetails = cookie.getValue() != null ? authentication.verifyJwtToken(cookie.getValue()) : null;
					}
				}

				if(userDetails == null)
					throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);


				userArticles = articleDaoMongo.getFavoritArticles(userDetails[1]);

				if(userArticles == null) return null;

				types.put(categories[0], 0);
				types.put(categories[1], 1);
				types.put(categories[2], 2);
				types.put(categories[3], 3);
				types.put(categories[4], 4);


				for (UserArticles userArticle : userArticles) {
					articles.get(types.get(userArticle.getType())).forEach(article ->{
						if (article.getTitle().contains(userArticle.getTitle())) {
							//TODO: Should add some property to shown which article user liked and which not
						}
					});
				}


				return articles;

			} catch (ApplicationException e) {
				throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
			}
	}
}

