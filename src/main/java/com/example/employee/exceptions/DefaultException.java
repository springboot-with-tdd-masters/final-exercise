package com.example.employee.exceptions;

import com.example.employee.exceptions.codes.ExceptionCode;
import com.example.employee.exceptions.codes.InternalServerErrorCode;
import org.springframework.http.HttpStatus;

public class DefaultException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public DefaultException() {
        this.exceptionCode = new InternalServerErrorCode();
    }

    public DefaultException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    @Override
    public String getMessage() {
        return exceptionCode.getMessage();
    }

    public HttpStatus getStatus() {
        return this.exceptionCode.getStatus();
    }
}
