package com.softvision.masters.tdd.employeeskilltracker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5545466885256769635L;

	public RecordNotFoundException() {
		super("No record(s) found.");
	}
	
	public RecordNotFoundException(String message) {
		super(message);
	}
	
	public RecordNotFoundException(String message, Throwable t) {
		super(message, t);
	}
}
