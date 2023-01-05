package com.gil.whatsnew.utils;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gil.whatsnew.enums.ErrorType;
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
			init();
		} catch (ApplicationException  e) {
			ExceptionHandler.generatedExceptions(e);
		}	
	}
	
	private void init() throws ApplicationException  {
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
	    }catch(InterruptedException | ApplicationException e) {
	    	throw new ApplicationException(ErrorType.Initialize_Error,ErrorType.Initialize_Error.getMessage(),false);
	    }
}
	
	private void generateArticlesFromStock() throws ApplicationException{
		eventPublisher.articleEvent();
		LoggingHandler.infoLogHandler(StringPaths.getLogs("InfoLogicLogFile"), "All articles are added successfully" + LocalTime.now().toString());
	}
}
