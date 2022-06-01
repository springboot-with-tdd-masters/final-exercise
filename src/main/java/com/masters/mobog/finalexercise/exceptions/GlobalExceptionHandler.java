package com.masters.mobog.finalexercise.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FinalExerciseException.class)
    public ResponseEntity<ErrorWrapper> handleBadRequestException(FinalExerciseException ex, WebRequest req){
        return ResponseEntity.status(ex.getStatus()).body(new ErrorWrapper(ex.getMessage()));
    }

}
