package com.finalexam.skills.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2433483504677527984L;

  public EmployeeNotFoundException() {
    super("Employee Not Found");
  }

  public EmployeeNotFoundException(String message) {
    super(message);
  }
}
