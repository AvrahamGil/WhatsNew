package com.gil.whatsnew.dao;

import java.util.ArrayList;
import java.util.List;


import com.gil.whatsnew.bean.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gil.whatsnew.bean.NewsApiDataStructure;
import com.gil.whatsnew.bean.UserArticles;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.interfaces.IArticlesDao;
import com.mongodb.client.result.UpdateResult;

@Repository
public class ArticleDao implements IArticlesDao{

	@Autowired
	MongoTemplate mongoTemplate;

	Logger logger = LoggerFactory.getLogger(ArticleDao.class);
	
	@Override
	public void addArticle(Article articles, String type)
			throws ApplicationException {
		try {
			List<String> types = new ArrayList<>();
			Query query = new Query(Criteria.where("id").is(articles.getArticleId()));
			types.add(type);
			if (!mongoTemplate.exists(query, Article.class)) {
				articles.setCategory(types.get(0));
				mongoTemplate.insert(articles);
				logger.info("Article added");
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean updateArticle(NewsApiDataStructure articles) throws ApplicationException {
		Query query = new Query();
		boolean updated = false;
		try {
			UpdateResult article = articles != null ? mongoTemplate.updateFirst(query.addCriteria(Criteria.where("id").is(articles.getArticle_id())),Update.update("isLiked", true), NewsApiDataStructure.class) : null;
			
			if(article != null) {
				updated = article.wasAcknowledged() ? true : false;
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
		
		query.addCriteria(Criteria.where("category").is(type));
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

		query.addCriteria(Criteria.where("category").is(type)).limit(140);
		
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
	public Article getArticleDetails(String id) throws ApplicationException {
		Query query = new Query();

		try {
			query.addCriteria(Criteria.where("id").is(id)).limit(1);
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
	public void deleteFavoritArticle(UserArticles like) throws ApplicationException {
		Query query = new Query();
		
		query.addCriteria(Criteria.where("userId").is(like.getUserId()).and("title").is(like.getTitle()));
		
		try {
			 mongoTemplate.findAndRemove(query,UserArticles.class);
		
		} catch(Exception e) {
			ExceptionHandler.generatedDaoExceptions(e);
		}

		logger.info("Unliked article success");
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

	private Update addArticle(NewsApiDataStructure article) {
		Update update = new Update();
		update.set("_id", article.getArticle_id());
		update.set("title", article.getSentiment());
		update.set("url", article.getLink());
		update.set("description", article.getDescription());
		update.set("language", article.getLanguage());
		update.set("image", article.getImage_url());
		update.set("author", article.getAi_org());
		update.set("published", article.getAi_tag());
		update.set("isLiked", article.getCreator());
		update.set("_class", article.getSource_name());

		return update;
	}
}
