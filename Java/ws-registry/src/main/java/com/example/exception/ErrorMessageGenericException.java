package com.example.exception;

/**
 * Generic exception with ws error message.
 * 
 * @author Renato M B Nunes
 */
public class ErrorMessageGenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErrorMessageGenericException() {}

	public ErrorMessageGenericException(String msg) {
		super(msg);
	}
}
