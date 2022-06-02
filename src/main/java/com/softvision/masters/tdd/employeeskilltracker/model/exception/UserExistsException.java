package com.softvision.masters.tdd.employeeskilltracker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserExistsException extends UnauthorizedException {
    public UserExistsException() {
        super("User already exists.");
    }
}
