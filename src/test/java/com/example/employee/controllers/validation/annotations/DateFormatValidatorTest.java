package com.example.employee.controllers.validation.annotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class DateFormatValidatorTest {

    private DateFormatValidator dateFormatValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        dateFormatValidator = new DateFormatValidator();
    }

    @Test
    @DisplayName("isValid_shouldReturnTrueWhenGivenIsNull")
    void isValid_shouldReturnTrueWhenGivenIsNull() {
        // Arrange

        // Act
        final boolean actual = dateFormatValidator.isValid(null, constraintValidatorContext);

        // Assert
        assertTrue(actual);
    }

    @Test
    @DisplayName("isValid_shouldReturnTrueWhenGivenIsValid")
    void isValid_shouldReturnTrueWhenGivenIsValid() {
        // Arrange
        dateFormatValidator.setPattern("yyyy-MM-dd");

        final String dateToCheck = "2022-06-03";

        // Act
        final boolean actual = dateFormatValidator.isValid(dateToCheck, constraintValidatorContext);

        // Assert
        assertTrue(actual);
    }

    @Test
    @DisplayName("isValid_shouldReturnFalseWhenGivenIsInvalid")
    void isValid_shouldReturnFalseWhenGivenIsInvalid() {
        // Arrange
        dateFormatValidator.setPattern("yyyy-MM-dd");

        final String dateToCheck = "invalid";

        // Act
        final boolean actual = dateFormatValidator.isValid(dateToCheck, constraintValidatorContext);

        // Assert
        assertFalse(actual);
    }
}