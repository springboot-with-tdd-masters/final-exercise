package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Employee findById(Long id);
    Employee createEmployee(EmployeeRequest employee);
    Page<Employee> findAll(Pageable pageable);
    Employee update(Long id, EmployeeRequest employee);
    void delete(Long id);
}
