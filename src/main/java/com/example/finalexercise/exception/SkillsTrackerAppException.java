package com.example.finalexercise.exception;

import org.springframework.http.HttpStatus;

public class SkillsTrackerAppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private SkillsTrackerAppExceptionCode code;

    public SkillsTrackerAppException(SkillsTrackerAppExceptionCode code){
        super(code.getMessage());
        this.code = code;
    }

    public HttpStatus getStatus(){
        return code.getStatus();
    }
    public String getMessage(){
        return super.getMessage();
    }
}