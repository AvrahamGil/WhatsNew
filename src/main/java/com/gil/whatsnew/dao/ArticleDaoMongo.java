package com.gil.whatsnew.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.UserArticles;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.bean.User;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.interfaces.IArticlesDao;
import com.mongodb.client.result.UpdateResult;

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

		} catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		logger.info("Article added");
	}
	
	@Override
	public boolean updateArticle(Article articles, NewYorkTimesApi newYorkArticles) throws ApplicationException {
		Query query = new Query();
		boolean updated = false;
		try {
			UpdateResult article = articles != null ? mongoTemplate.updateFirst(query.addCriteria(Criteria.where("id").is(articles.getId())),Update.update("isLiked", true),Article.class) : null;
			UpdateResult newYorkArticle = newYorkArticles != null ? mongoTemplate.updateFirst(query.addCriteria(Criteria.where("id").is(newYorkArticles.getId())),Update.update("isLiked", true),NewYorkTimesApi.class) : null;

			if(article != null) {
				updated = article.wasAcknowledged() ? true : false;
			}
			
			if(newYorkArticle != null) {
				updated = newYorkArticle.wasAcknowledged() ? true : false;
			}

			return updated;
			
		} catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		logger.info("Article updated");
		
		return false;
		
	}
	
	@Override
	public void deleteArticle(String type) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("newsType").is(type));
		try {
			 mongoTemplate.findAllAndRemove(query, Article.class);
			
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		logger.info("Delete articles success for type " + type);
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
	public Article getArticleDetails(String title) throws ApplicationException {
		Query query = new Query();

		try {
			query.addCriteria(Criteria.where("title").is(title)).limit(1);
			Article articleInStock = mongoTemplate.findOne(query, Article.class);
			
			if(articleInStock != null) return articleInStock;
			
			return null;
			
		}catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		return null;
	}
	
	@Override
	public void addFavoritArticle(UserArticles like) throws ApplicationException {
		try {
			UserArticles article = like != null ? mongoTemplate.save(like) : null;
		
		} catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}
		logger.info("Article added into favorit list");
		
	}

	@Override
	public boolean isArticleFavorated(String title, String userId) throws ApplicationException {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId).and("title").is(title));
	
		try {
			UserArticles articles = mongoTemplate.findOne(query, UserArticles.class);
		
			if(articles != null) return true;
			
			return false;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.Article_Already_Liked,ErrorType.Article_Already_Liked.getMessage(),false);
		} finally {
			query = null;
		}
	}

	@Override
	public List<UserArticles> getFavoritArticles(String userId) throws ApplicationException {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		
		try {
			List<UserArticles> articles = mongoTemplate.find(query,UserArticles.class);
			
			if(!articles.isEmpty()) return articles;
			
			return null;
			
		}catch(Exception e) {
			throw new ApplicationException(ErrorType.Get_List_Failed,ErrorType.Get_List_Failed.getMessage(),false);
		}
	}
}
