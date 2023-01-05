package com.gil.whatsnew.dao;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.enums.Keys;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.interfaces.IArticlesDao;
import com.gil.whatsnew.utils.StringPaths;
import com.gil.whatsnew.utils.JsonUtils;

@Repository
@Transactional
public class ArticleDao implements IArticlesDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;
	
	private final String path = "sites";

	Logger logger = LoggerFactory.getLogger(ArticleDao.class);
	
	@Override
	public void addArticle(Article articles,NewYorkTimesApi newYorkArticles,String value) throws ApplicationException {

		try {
			session = getSessionFactory().getCurrentSession();

			Query query = session.createSQLQuery(JsonUtils.readJsonFile(Keys.Create.getKey(), value, StringPaths.getPath(path)));
			
			Query article = articles != null ?  query.setParameter("Title", articles.getTitle()).setParameter("Description", articles.getDescription()).setParameter("Url", articles.getUrl()).setParameter("ImageUrl", articles.getImageUrl()).setParameter("ArticleSource", articles.getNewsType()) : null;
			Query newYorkArticle = newYorkArticles != null ? query.setParameter("Title", newYorkArticles.getTitle()).setParameter("Description", newYorkArticles.getDescription()).setParameter("Url", newYorkArticles.getUrl()).setParameter("ArticleSource", newYorkArticles.getNewsType()) : null;
	
			logger.info("Article addedd successfully");
			query.executeUpdate();
			
		}catch(ConstraintViolationException| NoResultException |EmptyResultDataAccessException | DataException  ex  ) {
		ExceptionHandler.generatedDaoExceptions(ex);
		} 
		
	}
	

	@Override
	public void deleteArticle(String value) throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(JsonUtils.readJsonFile(Keys.Delete.getKey(), value, StringPaths.getPath(path)));
			
			logger.info("Article remove successfully");
			query.executeUpdate();
			
		} catch (NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		
	}

	@Override
	public List<Article> getAllArticles(String value) throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(JsonUtils.readJsonFile(Keys.GetListOfArticles.getKey(), value, StringPaths.getPath(path)));
			List<Object[]> articlesObject = query.getResultList();
	
			if(articlesObject.isEmpty()) {
				return null;
			}
			
			List<Article>articles = new ArrayList<Article>();
			
			for (Object[] result : articlesObject) {
				Article article = new Article();
				
				article.setTitle(String.valueOf(result[0]));
				article.setDescription(String.valueOf(result[1]));
				article.setUrl(String.valueOf(result[2]));
				article.setImageUrl(String.valueOf(result[3]));
				article.setNewsType(String.valueOf(result[4]));

				articles.add(article);
			}
			

			return articles;

		} catch (DataException |NoResultException |EmptyResultDataAccessException ex) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	
	@Override
	public List<NewYorkTimesApi> getNewYorkTimesArticles(String articleType,String value) throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(JsonUtils.readJsonFile(Keys.GetNewYorkArticles.getKey(),value , StringPaths.getPath(path)));
			query.setParameter("articleSource", articleType);
			List<Object[]> articlesObject = query.getResultList();
			List<NewYorkTimesApi>articles = new ArrayList<NewYorkTimesApi>();
			
			if(articlesObject.isEmpty()) {
				return articles;
			}
			
			for (Object[] result : articlesObject) {
				NewYorkTimesApi article = new NewYorkTimesApi();
				
				article.setTitle(String.valueOf(result[0]));
				article.setDescription(String.valueOf(result[1]));
				article.setUrl(String.valueOf(result[2]));
				article.setNewsType(String.valueOf(result[3]));

				articles.add(article);
			}
			

			return articles;

		} catch (DataException |NoResultException |EmptyResultDataAccessException ex) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	

	@Override
	public List<Article> getRandomArticle(String value) throws ApplicationException {
		List<Article>articles = new ArrayList<Article>();
		
		try {
			
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery(JsonUtils.readJsonFile(Keys.GetRandom.getKey(),value , StringPaths.getPath(path)));
			Object[] result = (Object[]) query.getSingleResult();
			
			Article article = new Article();
			
			article.setTitle(String.valueOf(result[0]));
			article.setDescription(String.valueOf(result[1]));
			article.setUrl(String.valueOf(result[2]));
			article.setImageUrl(String.valueOf(result[3]));
			article.setNewsType(String.valueOf(result[4]));
	
			articles.add(article);
			
			return articles;

		} catch (DataException |NoResultException |EmptyResultDataAccessException ex) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	
	
	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
