package com.example.finalexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
}
