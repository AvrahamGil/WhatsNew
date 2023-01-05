package com.gil.whatsnew.dao;

import java.util.ArrayList;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.Multimedia;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.interfaces.IArticlesDao;

@Repository
public class ArticleDaoMongo implements IArticlesDao{

	@Autowired
	MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(ArticleDaoMongo.class);
	
	@Override
	public void addArticle(Article articles, NewYorkTimesApi newYorkArticles,String type)
			throws ApplicationException {
		try {
			if(articles != null ) articles.setType(type);
			Article article = articles != null ? mongoTemplate.save(articles) : null;
			NewYorkTimesApi newYorkArticle = newYorkArticles != null ? mongoTemplate.save(newYorkArticles) : null;

			if(article != null) logger.info("Article added"); 
			if(newYorkArticle != null) logger.info("News york times articles added"); 

		} catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		
	}
	
	@Override
	public void deleteArticle(String type) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("newsType").is(type));
		try {
			List<Article> articleInStock = mongoTemplate.findAllAndRemove(query, Article.class);
			
			if(articleInStock.isEmpty()) logger.info("Delete articles success for type " + type); 
			
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
	}
	
	@Override
	public List<Article> getAllArticles(String type) throws ApplicationException {
		Query query = new Query();

		query.addCriteria(Criteria.where("type").is(type)).limit(140);
		
		try {
			List<Article> articleInStock = mongoTemplate.find(query, Article.class);
			
			if(!articleInStock.isEmpty()) return articleInStock;
			
			return null;
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		return null;
	}
	
	@Override
	public List<NewYorkTimesApi> getNewYorkTimesArticles(String articleType, String type) throws ApplicationException {
		Query query = new Query();

		try {
			query.addCriteria(Criteria.where("newsType").is(type)).limit(30);
			List<NewYorkTimesApi> articleInStock = mongoTemplate.find(query, NewYorkTimesApi.class);
			
			if(!articleInStock.isEmpty()) return articleInStock;
			
			return null;
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		return null;
	
	}
	
	@Override
	public List<Article> getRandomArticle(String type) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("type").is(type)).limit(3);
		try {
			List<Article> articleInStock = mongoTemplate.find(query, Article.class);
			
			if(articleInStock != null) return articleInStock;
			
			return null;
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		return null;
	
	}
	
}
