package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.EmployeeDto;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.repository.EmployeesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeesRepository repo;

    @InjectMocks
    private EmployeesServiceImpl service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveHappyPath() throws RecordNotFoundException {
        EmployeeDto dto = new EmployeeDto();
        dto.setLastName("lastName");
        dto.setFirstName("firstName");
        Employees employees = new Employees();
        employees.setLastName("lastName");
        employees.setFirstName("firstName");
        employees.setId(Long.parseLong("1"));
        when(repo.save(Mockito.any(Employees.class))).thenReturn(employees);
        Employees employeeResponse = service.createOrUpdateEmployee(dto);
        verify(repo).save(Mockito.any(Employees.class));
        Assertions.assertEquals("lastName",employeeResponse.getLastName());
        Assertions.assertEquals("firstName",employeeResponse.getFirstName());
        Assertions.assertNotNull(employeeResponse.getId());
    }
}
