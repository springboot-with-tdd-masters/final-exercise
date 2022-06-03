package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


public class EmployeeServiceTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService service = new EmployeeServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {

        Employee employee = mock(Employee.class);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        //execute
        service.addEmployee(employee);

        //test
        verify(employeeRepository)
                .save(employee);

    }
}
