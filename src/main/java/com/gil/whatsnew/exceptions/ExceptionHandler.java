package com.gil.whatsnew.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

import com.gil.whatsnew.enums.ErrorType;

public class ExceptionHandler {

	public static Exception generatedLogicExceptions(ApplicationException e)
			throws ApplicationException {
		List<ApplicationException> exceptions = new ArrayList<ApplicationException>();
		exceptions.add(e);

		for (ApplicationException exception : exceptions) {

			if (exception.getErrorType().equals(ErrorType.Create_Failed)) {
				throw new ApplicationException(ErrorType.General_Error, "Content is not valid", true);
			}
			if (exception.getErrorType().equals(ErrorType.Update_Failed)) {
				throw new ApplicationException(ErrorType.General_Error, "Content include wrong details", true);
			}
			if (exception.getErrorType().equals(ErrorType.DaoException)) {
				throw new ApplicationException(ErrorType.General_Error, "Check your details again.", true);
			}
			
		}
		throw new ApplicationException(ErrorType.General_Error, e.getMessage(),true);

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
		}

		throw new ApplicationException(ErrorType.DaoException, e.getMessage(), true);
	}
}
