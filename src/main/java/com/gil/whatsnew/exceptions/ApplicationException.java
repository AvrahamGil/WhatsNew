package com.gil.whatsnew.exceptions;

import com.gil.whatsnew.enums.ErrorType;

public class ApplicationException extends Exception{

	private static final long serialVersionUID = 1L;

	private ErrorType errorType;
	private boolean suppressStacktrace = false;

	public ApplicationException(ErrorType errorType, String message, boolean suppressStacktrace) {
		super(message, null, suppressStacktrace, !suppressStacktrace);
		this.suppressStacktrace = suppressStacktrace;
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	@Override
	public String toString() {		
		if(suppressStacktrace)
			return getLocalizedMessage();
		
		return super.toString();
	}
}
