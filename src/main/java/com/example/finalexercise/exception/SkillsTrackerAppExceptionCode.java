package com.example.finalexercise.exception;

import org.springframework.http.HttpStatus;

public enum SkillsTrackerAppExceptionCode {
    EMPLOYEE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "There is no Employee with that ID"),
    SKILL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "There is no Skill with that ID"),
    RECORD_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no record with given IDs"),
    INVALID_DURATION_EXCEPTION(HttpStatus.BAD_REQUEST, "Please insert a valid duration period in years"),
    INVALID_DATE_EXCEPTION(HttpStatus.BAD_REQUEST, "Please insert a valid date. (Format: YYYY-MM-DD)"),
	UNABLE_TO_MAP_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Error in Mapping the response");

	private HttpStatus status;
    private String message;


    private SkillsTrackerAppExceptionCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}