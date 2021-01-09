package com.gil.whatsnew.logic;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.gil.whatsnew.enums.EventType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.listener.EventPublisher;


@Component
public class Timer implements Runnable{

	@Autowired
	private EventPublisher eventPublisher;
	
	@Override
	public void run() {
		try {
			checkTimeToUpdateArticles();
		} catch (Exception e) {
			try {
				ExceptionHandler.generatedDaoExceptions(e);
			} catch (ApplicationException e1 ) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void checkTimeToUpdateArticles() throws InterruptedException, ApplicationException, ClientProtocolException, IOException  {
		boolean isTime =true;
		boolean timeToUpdateArticles = false;
	    long displayMinutes = 0;
	    long starttime=System.currentTimeMillis();

	    try {
	    	  while(isTime)
	  	    {
	  	        TimeUnit.SECONDS.sleep(1);
	  	        timeToUpdateArticles = true;
	  	        long timePassed = System.currentTimeMillis()- starttime;
	  	        long secondspassed = timePassed / 1000;
	  	        
	  	        if(secondspassed==60)
	  	        {
	  	            secondspassed=0;
	  	            starttime=System.currentTimeMillis();
	  	        }
	  	        
	  	        if( (secondspassed%60) == 0) {
	  	        	displayMinutes++;
	  	        } 
	  	        
	  	        if(displayMinutes == 300 && timeToUpdateArticles) {
	  	        	generateArticlesFromStock();
	  	        	displayMinutes = 0;
	  	        	secondspassed = 0;
	  	        	starttime=System.currentTimeMillis();
	  	        	timeToUpdateArticles = false;
	  	        }
	  	}
	    }catch(ApplicationException e) {
	    	ExceptionHandler.generatedLogicExceptions(e);
	    }
}
	
	private void generateArticlesFromStock() throws ApplicationException{
		System.out.println("Starting generate articles from stock...");
		eventPublisher.newsEvenet(EventType.Get);
		eventPublisher.businessEvent(EventType.Get);
		eventPublisher.sportEvent(EventType.Get);
		eventPublisher.technologyEvent(EventType.Get);
		eventPublisher.travelEvent(EventType.Get);
		System.out.println("Done !");
	}

	
	



}
