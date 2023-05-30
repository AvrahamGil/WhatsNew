package com.gil.whatsnew.utils;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gil.whatsnew.bean.Article;
import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.listener.EventPublisher;
import com.gil.whatsnew.logic.ArticleLogic;

@Component
public class Timer implements Runnable {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private ArticleLogic articleLogic;

	@Override
	public void run() {
		try {
			init();
		} catch (ApplicationException e) {
			ExceptionHandler.generatedExceptions(e);
		}
	}

	private void init() throws ApplicationException {
		boolean timeToUpdateArticles = false;
		boolean firstTime = true;
		int displayMinutes = 0;
		int refresh = 300;
		long starttime = System.currentTimeMillis();

		try {
			while (true) {
				TimeUnit.SECONDS.sleep(1);

				long timePassed = System.currentTimeMillis() - starttime;
				long secondspassed = timePassed / 1000;

				if (secondspassed == 60) {
					secondspassed = 0;
					starttime = System.currentTimeMillis();
				}

				if ((secondspassed % 60) == 0) {
					displayMinutes++;
				}

				
				if (firstTime == true || articleLogic.getListOfNewsArticles(null).get(0) == null) {
					displayMinutes = refresh;
				}
					
				timeToUpdateArticles = displayMinutes == refresh ? true : false;

				if (timeToUpdateArticles) {
					generateArticlesFromStock();
					displayMinutes = 0;
					secondspassed = 0;
					starttime = System.currentTimeMillis();
					timeToUpdateArticles = false;
					firstTime = false;
				}
			}
		} catch (ApplicationException | InterruptedException e) {
			throw new ApplicationException(ErrorType.Initialize_Error, ErrorType.Initialize_Error.getMessage(), false);
		}
	}

	private void generateArticlesFromStock() throws ApplicationException {
		System.out.println("Generating articles from stock");
		//eventPublisher.articleEvent();
		System.out.println("Finish");
	}
}
