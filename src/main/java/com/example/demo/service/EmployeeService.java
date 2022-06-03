package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Employee createNewEmployee(EmployeeRequest employeeRequest);

    Employee readEmployeeById(Long id);

    Page<Employee> readAllEmployees(Pageable pageable);

    Employee updateEmployee(EmployeeRequest employeeRequest);

    void deleteEmployeeById(Long id);
}
