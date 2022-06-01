package com.masters.mobog.finalexercise.exceptions;


import org.springframework.http.HttpStatus;

public enum FinalExerciseExceptions {
    MAPPING_EXCEPTION(HttpStatus.BAD_REQUEST, "Unable to map request"),
    EMPLOYEE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Employee not found"),
    SKILL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Skill not found")

    ;

    private FinalExerciseExceptions(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
    private HttpStatus status;
    private String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
