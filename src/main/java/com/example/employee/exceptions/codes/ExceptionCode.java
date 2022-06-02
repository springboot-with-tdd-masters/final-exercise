package com.example.employee.exceptions.codes;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    HttpStatus getStatus();

    String getMessage();

}
