package com.gil.whatsnew.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gil.whatsnew.exceptions.ApplicationException;
import com.gil.whatsnew.exceptions.ExceptionHandler;

public class SessionUtils {

	private static HttpSession session;
	
	public static HttpSession getSession(HttpServletRequest request,String email, String password) throws ApplicationException  {
		boolean tokenVerified = false;
		boolean tokenExpired = false;
		
		session = request.getSession(false);
		
		try {
			if(session != null) {
				tokenVerified = TokenBuilder.verifyToken(session.getAttribute("token").toString(),email,password) ? true : false;
				
				if(tokenVerified) return null;
				
				tokenExpired = session.getCreationTime() <= System.currentTimeMillis() ? true : false;
				
				if(tokenExpired) {
					session.invalidate();
					return null;
				}
				
				return session;
				
			}  else {
				session = request.getSession(true);
				session.setAttribute("token", TokenBuilder.generatedToken(email,password));
				session.setMaxInactiveInterval(15*60);
				return session;
			}
			
		}catch(ApplicationException e) {
			ExceptionHandler.generatedLogicExceptions(e);
		}
		return null;
	}
}
