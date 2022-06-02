package com.example.employee.services.adapters;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAdapterTest {

    private EmployeeAdapter employeeAdapter;

    @BeforeEach
    void setUp() {
        employeeAdapter = new EmployeeAdapter();
    }

    @Test
    @DisplayName("convert_shouldAcceptEmployeeRequestAndConvertToEmployeeEntity")
    void convert_shouldAcceptEmployeeRequestAndConvertToEmployeeEntity() {
        // Arrange
        final String firstName = "First Name";
        final String lastName = "Last Name";

        final EmployeeRequest employeeRequest = EmployeeRequest.of(firstName, lastName);

        // Act
        final EmployeeEntity actual = employeeAdapter.convert(employeeRequest);

        // Assert
        assertNotNull(actual);
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertNull(actual.getId());
        assertNull(actual.getCreatedDate());
        assertNull(actual.getLastModifiedDate());
    }

    @Test
    @DisplayName("convert_shouldAcceptEmployeeEntityAndConvertToEmployee")
    void convert_shouldAcceptEmployeeEntityAndConvertToEmployee() {
        // Arrange

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");

        final Long id = 1L;
        final String firstName = "First Name";
        final String lastName = "Last Name";
        final LocalDateTime now = LocalDateTime.now();

        final EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setId(id);
        employeeEntity.setFirstName(firstName);
        employeeEntity.setLastName(lastName);
        employeeEntity.setCreatedDate(now);
        employeeEntity.setLastModifiedDate(now);

        // Act
        final Employee actual = employeeAdapter.convert(employeeEntity);

        // Assert
        assertNotNull(actual);
        assertEquals(id, actual.getId());
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());
        assertEquals(now.format(formatter), actual.getCreatedDate());
        assertEquals(now.format(formatter), actual.getLastModifiedDate());
    }
}