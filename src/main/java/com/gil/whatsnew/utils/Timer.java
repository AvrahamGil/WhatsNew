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
		int displayMinutes = 0;
		int refresh = 300;
		boolean firstTime = true;
		boolean timeToUpdateArticles = true;

		while(true) {
			try {
				long starttime = System.currentTimeMillis();

				TimeUnit.SECONDS.sleep(1);

				long timePassed = System.currentTimeMillis() - starttime;
				long secondspassed = timePassed / 1000;

				if (secondspassed == 60) {
						secondspassed = 0;
				}

				if ((secondspassed % 60) == 0) {
						displayMinutes++;
				}


				if (firstTime) {
						displayMinutes = refresh;
				}

				timeToUpdateArticles = displayMinutes == refresh;

				if (timeToUpdateArticles) {
					generateArticlesFromStock();
					displayMinutes = 0;
					timeToUpdateArticles = false;
					firstTime = false;
				}


			} catch (ApplicationException | InterruptedException e) {
				ExceptionHandler.addExceptionIntoLog(e);
			}
		}

	}

	private void generateArticlesFromStock() throws ApplicationException {
		System.out.println("Generating articles from stock");
		//eventPublisher.articleEvent();
		System.out.println("Finish");
	}
}
