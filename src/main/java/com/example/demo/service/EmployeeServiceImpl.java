package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createNewEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.save(buildEmployee(employeeRequest));
    }

    private Employee buildEmployee(EmployeeRequest employeeRequest) {
        return Employee.builder().firstName(employeeRequest.getFirstName()).lastName(employeeRequest.getLastName()).build();
    }

    public Employee readEmployeeById(Long id) {
        Optional<Employee> retrievedEmployee = employeeRepository.findById(id);
        if (retrievedEmployee.isEmpty()) {
            throw new NotFoundException(String.format("Employee with id %s not found", id));
        }
        return retrievedEmployee.get();
    }

    public Page<Employee> readAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Transactional
    public Employee updateEmployee(EmployeeRequest employeeRequest) {
        Optional<Employee> retrievedEmployee = employeeRepository.findById(employeeRequest.getId());
        if(retrievedEmployee.isEmpty()){
            throw new NotFoundException(String.format("Employee with id %s not found", employeeRequest.getId()));
        }

        Employee oldEmployee = retrievedEmployee.get();
        oldEmployee.setFirstName(employeeRequest.getFirstName());
        oldEmployee.setLastName(employeeRequest.getLastName());

        return employeeRepository.save(oldEmployee);
    }


    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
