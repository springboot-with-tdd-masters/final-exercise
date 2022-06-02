package com.example.employee.exceptions.codes;

import org.springframework.http.HttpStatus;

public enum EmployeeExceptionCode implements ExceptionCode {

    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "Employee not found.");

    final HttpStatus status;

    final String message;

    EmployeeExceptionCode(HttpStatus status, String message) {
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
