package com.springrest.springrest.ui.model.response;

import java.util.Date;

public class Response {

	private Object data;
	private int status;
	private String message;
	private Date timestamp;

	public Response(Object data, int status, String message) {
		this.timestamp = new Date();
		this.data = data;
		this.status = status;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
