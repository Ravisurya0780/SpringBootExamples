package com.incture.example.exception;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp;
	private String field;
	private String message;
	private String details;

	public ExceptionResponse(Date timestamp, String field, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.field = field;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public String getField() {
		return field;
	}

}