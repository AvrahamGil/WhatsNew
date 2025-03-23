package com.gil.whatsnew.api;

import java.util.List;


import com.gil.whatsnew.bean.Article;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gil.whatsnew.bean.NewsApiDataStructure;
import com.gil.whatsnew.bean.Login;

import com.gil.whatsnew.enums.Cookies;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.logic.ArticleLogic;
import com.gil.whatsnew.utils.Authentication;


@RestController
@RequestMapping("/rest/api/articles")
public class ArticleApi {

	@Autowired
	private ArticleLogic article;
	
	@Autowired
	private Authentication authentication;
	
	@RequestMapping(value="/getNewsArticles" , method = RequestMethod.GET)
	public List<List<Article>>getNewsArticles(HttpServletRequest request) throws ApplicationException {
		List<List<Article>>articles = article.getListOfNewsArticles(request);
		if(articles != null) return articles;
		
		return null;
	}

	@RequestMapping(value="/liked" , method = RequestMethod.POST)
	public ResponseEntity<Object> addToFavorite(@RequestBody String articleId , HttpServletRequest request) throws ApplicationException {
			try {
				if(authentication.verifyCookies(request)) {
					ResponseEntity<Object> res = article.addIntoFavorite(articleId, request);
					
					if(res == null) throw new ApplicationException(ErrorType.General_Error,"One or more details are incorrect",true);
					
					return res;

				}
			}catch(ApplicationException e) {
				ExceptionHandler.generatedLogicExceptions(e);
			}
			return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	

	@RequestMapping(value="/favoriteArticles" , method = RequestMethod.GET)
	public List<NewsApiDataStructure> getFavoriteArticles(@RequestBody Login loginDetail, HttpServletRequest request) throws ApplicationException {
		try {
			if(authentication.verifyCookies(request)) {
				article.getFavoriteArticles(request);
			}
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		
		return null;
	}
	
	private ResponseEntity<Object>response(HttpServletResponse response) throws ApplicationException {
		String newToken = authentication.generateCSRFToken();

		
		HttpHeaders headers =new HttpHeaders();
		headers.add(Cookies.XCSRFTOKEN.getName(),newToken);

		ResponseCookie csrfCookie = ResponseCookie.from(Cookies.XCSRFTOKEN.getName(), newToken)
	            .httpOnly(true)
	            .sameSite("None")
	            .secure(true)
	            .path("/")
	            .maxAge(Math.toIntExact(15 * 60))
	            .build();
		
		response.addHeader("Set-Cookie", csrfCookie.toString());
		
		ResponseEntity<Object> res = new ResponseEntity<Object>(headers,HttpStatus.OK);
		
		return res;
	}
}
