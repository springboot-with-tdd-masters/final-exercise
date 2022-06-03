package com.java.finalexercise.service;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService = new EmployeeServiceImpl();

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save ")
    public void save() {
        Employee employee = new Employee("John", "Xina");

        when(employeeService.save(employee))
                .thenReturn(employee);

        Employee savedEmployee = employeeService.save(employee);

        assertThat(employee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(savedEmployee.getLastName());
    }

    @Test
    @DisplayName("Update ")
    public void update() throws EmployeeNotFoundException {
        Employee employee = new Employee("John", "Xina");

        when(employeeRepository.findById(employee.getId()))
                .thenReturn(Optional.of(employee));

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        Employee savedEmployee = employeeService.update(employee);

        assertThat(employee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(savedEmployee.getLastName());
    }

    @Test
    @DisplayName("Find By ID: ")
    public void findById() throws Exception {
        Employee employee = new Employee("John", "Xina");

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Employee retrievedEmployee = employeeService.findById(1L);

        assertThat(retrievedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(retrievedEmployee.getLastName()).isEqualTo(employee.getLastName());

    }

    @Test
    @DisplayName("Find: ")
    public void find() {
        PageRequest pageRequest = PageRequest.of(0,10 , Sort.by(Sort.Direction.ASC, "firstName"));

        Page<Employee> employeesPage = Mockito.mock(Page.class);

        when(employeeRepository.findAll(pageRequest))
                .thenReturn(employeesPage);

        Page retrievedEmployeeListPage = employeeService.find(pageRequest);

        assertThat(retrievedEmployeeListPage).isEqualTo(employeesPage);
    }

}
