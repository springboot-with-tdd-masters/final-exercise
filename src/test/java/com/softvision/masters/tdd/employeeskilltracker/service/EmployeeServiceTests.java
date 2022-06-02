package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
import com.softvision.masters.tdd.employeeskilltracker.repository.EmployeeRepository;
import com.softvision.masters.tdd.employeeskilltracker.repository.SkillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    EmployeeRepository mockEmployeeRepository;
    @Mock
    SkillRepository mockSkillRepository;

    @InjectMocks
    EmployeeService employeeService = new EmployeeServiceImpl();

    @Mock
    Pageable mockPageable;
    @Mock
    Page<Employee> mockEmployeePage;
    @Mock
    Employee mockEmployee;
    @Mock
    Employee mockEmployeeExpected;

    @Test
    @DisplayName("Create - should create an employee")
    void test_create() {
        when(mockEmployeeRepository.save(mockEmployee)).thenReturn(mockEmployeeExpected);
        assertSame(mockEmployeeExpected, employeeService.create(mockEmployee));

        verify(mockEmployeeRepository, atMostOnce()).save(any());
    }

    @Test
    @DisplayName("Get All - should return a paged employee")
    void test_getAll() {
        when(mockEmployeePage.hasContent()).thenReturn(true);
        when(mockEmployeeRepository.findAll(mockPageable)).thenReturn(mockEmployeePage);
        assertSame(mockEmployeePage, employeeService.getAll(mockPageable));

        verify(mockEmployeeRepository, atMostOnce()).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Get Name Containing - should return a paged employee")
    void test_getNameContaining() {
        final String mockNameContaining = "name";
        when(mockEmployeePage.hasContent()).thenReturn(true);
        when(mockEmployeeRepository
                .findByFirstnameContainingOrLastnameContaining(mockNameContaining, mockNameContaining, mockPageable))
                .thenReturn(mockEmployeePage);

        assertSame(mockEmployeePage, employeeService.getNameContaining(mockNameContaining, mockPageable));

        verify(mockEmployeeRepository, atMostOnce())
                .findByFirstnameContainingOrLastnameContaining(anyString(), anyString(), any(Pageable.class));
    }

    @Test
    @DisplayName("Get All - should throw a RecordNotFoundException when repository returns no content")
    void test_getAll_throwRecordNotFoundException() {
        when(mockEmployeePage.hasContent()).thenReturn(false);
        when(mockEmployeeRepository.findAll(mockPageable)).thenReturn(mockEmployeePage);

        assertThrows(RecordNotFoundException.class, () -> employeeService.getAll(mockPageable));

        verify(mockEmployeeRepository, atMostOnce()).findAll(any(Pageable.class));
    }

}
