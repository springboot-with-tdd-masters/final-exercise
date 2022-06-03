package com.example.employee.controllers.validation.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    private String pattern;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String toValidate, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(toValidate)) {
            return true;
        }

        try {

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            LocalDate.parse(toValidate, formatter);

            return true;

        } catch (Exception exception) {
            return false;
        }
    }

    // NOTE: only used for testing
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
