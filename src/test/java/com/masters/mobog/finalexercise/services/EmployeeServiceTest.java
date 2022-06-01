package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    private EmployeeService service;

    @Mock
    private EmployeeRepository repository;

    @BeforeEach
    void setup(){
        service = new EmployeeServiceImpl(repository);
    }

    @Test
    @DisplayName("should create employee")
    void shouldCreateEmployee() {

    }
    @Test
    @DisplayName("should find employee")
    void shouldFindEmployee() {

    }
    @Test
    @DisplayName("should find employee with page")
    void shouldFindEmployeesWithPage() {

    }
    @Test
    @DisplayName("should update employee")
    void shouldUpdateEmployee() {

    }
    @Test
    @DisplayName("should delete employee")
    void shouldDeleteEmployee() {

    }
}
