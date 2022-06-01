package com.masters.mobog.finalexercise.exceptions;


import org.springframework.http.HttpStatus;

public enum FinalExerciseExceptionsCode {
    MAPPING_EXCEPTION(HttpStatus.BAD_REQUEST, "Unable to map request"),
    EMPLOYEE_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Employee not found"),
    SKILL_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "Skill not found"),
    JWT_EXCEPTION(HttpStatus.FORBIDDEN, "Forbidden"),
    JWT_USER_EXISTS(HttpStatus.CONFLICT, "Conflict"),
    JWT_INCOMPLETE_DTLS(HttpStatus.BAD_REQUEST, "Incomplete user details"),
    JWT_UNABLE_TO_PROCESS(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process request")


    ;

    FinalExerciseExceptionsCode(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
    private final HttpStatus status;
    private final String message;

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
