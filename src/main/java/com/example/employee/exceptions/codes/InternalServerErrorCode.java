package com.example.employee.exceptions.codes;

import org.springframework.http.HttpStatus;

public class InternalServerErrorCode implements ExceptionCode {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public String getMessage() {
        return "Unable to process your request";
    }
}
