package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class EmployeeRepositoryTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    private List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        employeeList.add(buildEmployee(1L, "James", "Bond"));
        employeeList.add(buildEmployee(2L, "Zea", "Abad"));

    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close() {
        employeeList = new ArrayList<>();
    }

    @Test
    @DisplayName("Should return all employees")
    public void testFindAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> retrievedEmployees = employeeRepository.findAll();
        assertEquals(2, retrievedEmployees.size());
    }

    @Test
    @DisplayName("Should return employee corresponding to provided employee ID")
    public void testFindEmployeeById() {
        Long requestedId = 1L;
        List<Employee> filteredEmployee = employeeList.stream().filter(e -> e.getId().equals(requestedId)).collect(Collectors.toList());
        when(employeeRepository.findById(requestedId)).thenReturn(Optional.of(filteredEmployee.get(0)));
        Optional<Employee> retrievedEmployee = employeeRepository.findById(requestedId);
        assertEquals(requestedId, retrievedEmployee.get().getId());
    }

}