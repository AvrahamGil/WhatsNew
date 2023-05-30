package com.gil.whatsnew.exceptions;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

import com.gil.whatsnew.enums.ErrorType;
import com.gil.whatsnew.utils.LoggingHandler;
import com.gil.whatsnew.utils.StringPaths;

public class ExceptionHandler {

	public static Exception generatedLogicExceptions(ApplicationException e)
			throws ApplicationException {
		
		List<ApplicationException> exceptions = new ArrayList<ApplicationException>();
		exceptions.add(e);
		
		for (ApplicationException exception : exceptions) {
			
			if (exception.getErrorType().equals(ErrorType.General_Error)) {
				throw new ApplicationException(ErrorType.General_Error, "Something went wrong", true);
			}
			if (exception.getErrorType().equals(ErrorType.Create_Failed)) {
				throw new ApplicationException(ErrorType.General_Error, "Content is not valid, please check your email", true);
			}
			if (exception.getErrorType().equals(ErrorType.Update_Failed)) {
				throw new ApplicationException(ErrorType.General_Error, "Content include wrong details", true);
			}
			if (exception.getErrorType().equals(ErrorType.DaoException)) {
				throw new ApplicationException(ErrorType.General_Error, "Check your details again.", true);
			}
		}
		throw new ApplicationException(ErrorType.General_Error, ErrorType.General_Error.getMessage(),true);

	}

	public static Exception generatedDaoExceptions(Exception e) throws ApplicationException {

		List<Exception> exceptions = new ArrayList<Exception>();
		exceptions.add(e);
		
		for (Exception exception : exceptions) {
			if (exception.getClass().equals(DataException.class))
				throw new ApplicationException(ErrorType.DaoException, "Details are not spell correctly", true);
			if (exception.getClass().equals(NoResultException.class))
				throw new ApplicationException(ErrorType.DaoException, "No such data", true);
			if(exception.getClass().equals(ConstraintViolationException.class))
				throw new ApplicationException(ErrorType.DaoException, "Details are incorrect", true);
			if(exception.getClass().equals(InterruptedException.class))
				throw new ApplicationException(ErrorType.DaoException, "Details are incorrect", true);
		}

		throw new ApplicationException(ErrorType.DaoException, e.getMessage(), true);
	}
	
	public static String generatedExceptions(ApplicationException e)  {
		List<ApplicationException> exceptions = new ArrayList<ApplicationException>();
		exceptions.add(e);
		
		for (ApplicationException exception : exceptions) {
			if (exception.getErrorType().equals(ErrorType.Domains_Failed))
				return ErrorType.Domains_Failed.getMessage();
			if (exception.getErrorType().equals(ErrorType.Initialize_Error))
				return ErrorType.Initialize_Error.getMessage();
			if(exception.getErrorType().equals(ErrorType.Read_Json_Failed))
				return ErrorType.Read_Json_Failed.getMessage();
		}
		
		return ErrorType.General_Error.getMessage();
	}
	
	public static String addExceptionIntoLog(Exception e)  {				
		return ErrorType.General_Error.getMessage();
	}
}
