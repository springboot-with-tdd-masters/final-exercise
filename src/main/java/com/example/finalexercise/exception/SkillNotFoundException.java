package com.example.finalexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {

	public SkillNotFoundException() {
		super("SKill Not Found");
	}

}
