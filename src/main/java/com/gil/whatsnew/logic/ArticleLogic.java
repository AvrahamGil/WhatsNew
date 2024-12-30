package com.gil.whatsnew.logic;

import java.io.IOException;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.UserArticles;
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
	
	private final String[] categories = { "news", "business", "sport", "technology", "travel" };
	private final String specialChar = "~`!#$%^&*(){}[]+-_=/><:\"\\?;";
	
	private final String path = "domains";
	private final int maxDomains = 13;

	private int counter = 1;
	private int maxCounter = 7;

	public void addArticlesToStock(Set<Article> jsonArticles, String category) throws ApplicationException {
		try {
			if (!jsonArticles.isEmpty()) {
				for (Article article : jsonArticles) {
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

			if(request != null && request.getCookies() != null) {
				List<List<Article>> likedArticles = getArticlesLiked(request,articles); 

				
				likedArticles = likedArticles != null ? likedArticles : null;
				

				
				likedArticles = likedArticles != null ? likedArticles : null;
				
				if(likedArticles != null) return likedArticles;
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
			TimeUnit.SECONDS.sleep(5);
			List<Article> tempArticles = generatedArticleList(response);
			String lastTitle = "";
			String lastUrl = "";

			for (Article article : tempArticles) {
				if (article.getUrl().contentEquals(type) || counter <= maxCounter
						|| !article.getImage().isEmpty() && !lastTitle.equals(article.getTitle())
								&& !lastUrl.equals(article.getUrl())) {

					article.setImage(article.getImage());
					article.setAuthor(type);
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
		} catch (ApplicationException | InterruptedException e) {
			throw new ApplicationException(ErrorType.Api_Failed, ErrorType.Api_Failed.getMessage(), false);
		}

	}


	public ResponseEntity<Object> addIntoFavorit(String articleId,HttpServletRequest request) throws ApplicationException {
		UserArticles details = null;
		String randomId = UUID.randomUUID().toString();
		boolean verify = true;

		String[] userDetails = new String[2];
		
		try {
			if (articleId == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
			
			long id = Long.valueOf(articleId);
			
			if(id < 10000)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
			
			Article articles = articleDaoMongo.getArticleDetails(articleId);
			
			if(articles == null)
				throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(), false);
			
			details = new UserArticles();
			
			details.setId(randomId);
			details.setTitle(articles.getTitle());
			details.setType(articles.getPublished());

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
			
			if(!verify || userDetails == null) 
				return null;

			boolean liked = articleDaoMongo.isArticleFavorated(articles.getTitle(),userDetails[1]);

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
			
			if(userDetails == null)
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

	private List<Article> generatedArticleList(HttpResponse response) throws ApplicationException {

		Gson gson = new Gson();

		try {
			HttpEntity entity = response.getEntity();

			String responseString = EntityUtils.toString(entity);

			JSONObject json = new JSONObject(responseString);

			JSONArray items = json.getJSONArray("news");

			TypeToken<List<Article>> token = new TypeToken<List<Article>>() {
			};

			List<Article> tempArticles = gson.fromJson(items.toString(), token.getType());

			return tempArticles;

		} catch (ParseException | IOException e) {
			throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
		}

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
							article.setLiked(true);
						}
					});
				}
				
				
				return articles;
				
			} catch (ApplicationException e) {
				throw new ApplicationException(ErrorType.Get_List_Failed, ErrorType.Get_List_Failed.getMessage(), false);
			}
	}
}
	
