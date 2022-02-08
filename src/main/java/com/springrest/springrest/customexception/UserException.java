package com.springrest.springrest.customexception;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {
    @SuppressWarnings("unused")
	private String message;
    public UserException(String message) {
        super(message);
        this.message = message;
    }
  }