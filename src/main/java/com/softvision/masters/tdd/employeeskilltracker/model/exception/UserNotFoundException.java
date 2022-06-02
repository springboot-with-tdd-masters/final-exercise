package com.softvision.masters.tdd.employeeskilltracker.model.exception;

public class UserNotFoundException extends UnauthorizedException {
    public UserNotFoundException() {
        super("User not found.");
    }
}
