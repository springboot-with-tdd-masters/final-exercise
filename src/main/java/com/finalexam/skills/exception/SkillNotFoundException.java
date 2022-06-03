package com.finalexam.skills.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2433483509677527984L;

  public SkillNotFoundException() {
    super("Employee Not Found");
  }

  public SkillNotFoundException(String message) {
    super(message);
  }
}
