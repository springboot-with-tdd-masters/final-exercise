package com.example.employee.controllers.advices;

import com.example.employee.exceptions.DefaultException;
import com.example.employee.exceptions.codes.InternalServerErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleUnknownError(RuntimeException runtimeException, WebRequest request) {

        final InternalServerErrorCode internalServerErrorCode = new InternalServerErrorCode();

        return ResponseEntity
                .status(internalServerErrorCode.getStatus())
                .body(internalServerErrorCode.getMessage());
    }

    @ExceptionHandler(DefaultException.class)
    public final ResponseEntity<Object> handleError(DefaultException defaultException, WebRequest webRequest) {
        return ResponseEntity
                .status(defaultException.getStatus())
                .body(defaultException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {

        final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    validationErrorResponse.addViolation(fieldError.getField(), fieldError.getDefaultMessage());
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationErrorResponse);
    }

}
