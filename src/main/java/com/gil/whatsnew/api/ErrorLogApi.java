package com.gil.whatsnew.api;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gil.whatsnew.bean.ErrorLog;
import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.Authentication;

@RestController
@RequestMapping("/errors")
public class ErrorLogApi {

		@Autowired
		private Authentication authentication;
		
		@RequestMapping(value="/errorlog" , method = RequestMethod.POST)
		public void getNewsArticles(HttpServletRequest request,@RequestBody ErrorLog errorLog) throws ApplicationException {			
			try {
				authentication.errorLogFile(errorLog);
			} catch(ApplicationException e) {
				ExceptionHandler.generatedLogicExceptions(e);
			}
		}
}
