package com.gil.whatsnew.api;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gil.whatsnew.exceptions.ExceptionHandler;
import com.gil.whatsnew.utils.LoggingHandler;
import com.gil.whatsnew.utils.StringPaths;


@WebFilter("/rest/api/user/*")
public class LoginFilter implements Filter{

	private String redirectToo = "https://whatsnew.me";
	private final String filter = "Filtering";
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HttpSession session;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		RequestDispatcher requestDispatcher = null;
		req = (HttpServletRequest) request;
		res = (HttpServletResponse) response;
		
		try {
			session = req.getSession(false);
			
			boolean isLoggedIn = (session != null && session.getAttribute("token") != null);
			 
			String path = req.getRequestURI().substring(req.getContextPath().length());

			if (isLoggedIn && path.startsWith("/rest/api/user")) {
				chain.doFilter(request, response);
			} else {
				LoggingHandler.infoLogHandler(StringPaths.getLogs("ExceptionLogicLogFile"), filter);
			  	res.setStatus(400);
			  	requestDispatcher = request.getRequestDispatcher(redirectToo);
			  	requestDispatcher.include(request, response);
			}
			
		}catch(Exception e) {
			ExceptionHandler.addExceptionIntoLog(e);
		}
	}
}
