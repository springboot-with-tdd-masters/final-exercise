package com.java.finalexercise.service;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    public Employee save(Employee employee);

    public Employee update(Employee employee) throws EmployeeNotFoundException;

    public Long delete(Long id) throws EmployeeNotFoundException, EmployeeNotFoundException;

    public Employee findById(Long id) throws EmployeeNotFoundException;

    public Page<Employee> find(Pageable pageable);
}
