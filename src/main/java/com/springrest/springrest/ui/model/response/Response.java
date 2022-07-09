package com.springrest.springrest.ui.model.response;

import org.springframework.http.HttpStatus;

public class Response {

	private Object data;
	private HttpStatus status;
	private String message;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
