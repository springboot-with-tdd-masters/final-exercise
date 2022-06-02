package com.example.employee.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    @DisplayName("format_shouldReturnFormattedDateAsString")
    void format_shouldReturnFormattedDateAsString() {
        // Arrange
        final LocalDateTime toFormat = LocalDateTime.of(2022, 6, 2, 12, 38);

        // Act
        final String actual = DateUtils.format(toFormat);

        // Assert
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals("2022-06-02", actual);
    }

    @Test
    @DisplayName("format_shouldReturnEmptyStringIfDateIsNull")
    void format_shouldReturnEmptyStringIfDateIsNull() {
        // Arrange
        // Act
        final String actual = DateUtils.format(null);

        // Assert
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
        assertEquals("", actual);
    }
}