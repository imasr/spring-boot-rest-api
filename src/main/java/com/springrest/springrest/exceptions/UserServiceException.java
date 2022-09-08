package com.springrest.springrest.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 5946099558912351278L;

	public UserServiceException(String message) {
		super(message);
	}
}
