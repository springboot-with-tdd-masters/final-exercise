package com.example.employee.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum SkillExceptionCode implements ExceptionCode {

    ;

    final HttpStatus status;

    final String message;

    SkillExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
