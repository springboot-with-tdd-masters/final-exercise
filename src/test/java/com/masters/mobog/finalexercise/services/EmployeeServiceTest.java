package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        // given
        Employee forSaving = new Employee();
        forSaving.setFirstname("Jay");
        forSaving.setLastname("Rock");
        forSaving.setCreatedDate(LocalDateTime.now());
        forSaving.setLastModifiedDate(LocalDateTime.now());

        Employee stub = new Employee();
        stub.setId(1L);
        stub.setFirstname("Jay");
        stub.setLastname("Rock");
        stub.setCreatedDate(LocalDateTime.now());
        stub.setLastModifiedDate(LocalDateTime.now());
        when(repository.save(any(Employee.class))).thenReturn(stub);
        // when
        Employee actual = service.createEmployee(forSaving);
        verify(repository).save(any(Employee.class));
        assertNotNull(actual.getId());
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
