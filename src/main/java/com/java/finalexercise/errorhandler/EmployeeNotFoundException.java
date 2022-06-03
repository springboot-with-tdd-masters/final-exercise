package com.java.finalexercise.errorhandler;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException() {
        super("Employee Not Found Exception.");
    }
}
