package com.example.employee.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum SkillExceptionCode implements ExceptionCode {

    SKILL_NOT_FOUND(HttpStatus.NOT_FOUND, "Skill not found.")

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
