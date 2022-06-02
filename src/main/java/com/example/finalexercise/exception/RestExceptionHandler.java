package com.example.finalexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handleAllException(Exception e, WebRequest req) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<String> handleBusinessException(Exception e, WebRequest req) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<String> handleEmployeeNotFoundException(Exception e, WebRequest req) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SkillNotFoundException.class)
	public final ResponseEntity<String> handleSkillNotFoundException(Exception e, WebRequest req) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
