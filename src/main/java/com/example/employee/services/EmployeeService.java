package com.example.employee.services;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<Employee> findAll(Pageable pageRequest);

    Employee findById(Long id);

    Employee create(EmployeeRequest employeeRequest);

    Employee update(Long id, EmployeeRequest employeeRequest);

    void delete(Long id);

}
