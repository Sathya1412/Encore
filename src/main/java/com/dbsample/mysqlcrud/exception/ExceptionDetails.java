package com.dbsample.mysqlcrud.exception;

import org.springframework.http.HttpStatus;

public class ExceptionDetails {
	private final String message;
	private final HttpStatus httpStatus;
	private final String timestamp;
	
	public ExceptionDetails(String message, HttpStatus httpStatus, String string) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = string;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
}
