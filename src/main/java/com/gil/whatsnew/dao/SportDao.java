package com.gil.whatsnew.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;


import com.gil.whatsnew.bean.ContextApi;
import com.gil.whatsnew.bean.NewYorkTimesApi;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.interfaces.IArticlesDao;

@Repository
@Transactional
public class SportDao implements IArticlesDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;
	

	@Override
	public void addArticle(ContextApi article) throws ApplicationException {
		try {
				session = getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery("insert into sport (Title , Description , Url , ImageUrl ,SportType) "
						+ "value (:Title, :Description, :Url, :ImageUrl,:SportType)");
				query.setParameter("Title", article.getTitle());
				query.setParameter("Description", article.getDescription());
				query.setParameter("Url", article.getUrl());
				query.setParameter("ImageUrl", article.getImageUrl());
				query.setParameter("SportType", article.getNewsType());
				query.executeUpdate();
		}catch(ConstraintViolationException| NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} finally {
		
		}
		
	}


	public void addNewYorkArticle(NewYorkTimesApi article) throws ApplicationException {
		try {
				session = getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery("insert into sport (Title , Description , Url , ImageUrl ,SportType) "
						+ "value (:Title, :Description, :Url, :ImageUrl,:SportType)");
				query.setParameter("Title", article.getTitle());
				query.setParameter("Description", article.getDescription());
				query.setParameter("Url", article.getUrl());
				query.setParameter("ImageUrl", article.getUrlToImage());
				query.setParameter("SportType", article.getNewsType());
				query.executeUpdate();
		}catch(ConstraintViolationException| NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} finally {
		
		}
		
	}
	
	@Override
	public List<ContextApi> getAllArticles() throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery("select Title,Description,Url,ImageUrl,SportType from sport");
			List<Object[]> articlesObject = query.getResultList();
	
			if(articlesObject.isEmpty()) {
				return null;
			}
			
			List<ContextApi>articles = new ArrayList<ContextApi>();
			
			for (Object[] result : articlesObject) {
				ContextApi article = new ContextApi();
				
				article.setTitle(String.valueOf(result[0]));
				article.setDescription(String.valueOf(result[1]));
				article.setUrl(String.valueOf(result[2]));
				article.setImageUrl(String.valueOf(result[3]));
				article.setNewsType(String.valueOf(result[4]));

				articles.add(article);
			}
			

			return articles;

		} catch (NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	
	public List<NewYorkTimesApi> getNewYorkTimesArticles(String sportType) throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery("select Title,Description,Url,ImageUrl,SportType from sport where binary SportType = :sportType");
			query.setParameter("sportType", sportType);
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
				article.setUrlToImage(String.valueOf(result[3]));
				article.setNewsType(String.valueOf(result[4]));

				articles.add(article);
			}
			

			return articles;

		} catch ( NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	
	public ContextApi getRandomArticle() throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery("select Title,Description,Url,ImageUrl,SportType from sport order by RAND() limit 1");
			Object[] result = (Object[]) query.getSingleResult();
			
			ContextApi article = new ContextApi();
			
			article.setTitle(String.valueOf(result[0]));
			article.setDescription(String.valueOf(result[1]));
			article.setUrl(String.valueOf(result[2]));
			article.setImageUrl(String.valueOf(result[3]));
			article.setNewsType(String.valueOf(result[4]));
	
	
			return article;

		} catch (NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		return null;
	}
	
	@Override
	public void deleteArticle() throws ApplicationException {
		try {
			session = getSessionFactory().getCurrentSession();
			Query query = session.createSQLQuery("delete from sport");
			query.executeUpdate();
			
		} catch (NoResultException |EmptyResultDataAccessException | DataException  ex ) {
			ExceptionHandler.generatedDaoExceptions(ex);
		} 
		
	}

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
