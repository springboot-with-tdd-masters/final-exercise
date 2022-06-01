package com.example.employeeTDD.controller;

import com.example.employeeTDD.exception.RecordNotFoundException;
import com.example.employeeTDD.model.Employee;
import com.example.employeeTDD.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employees")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getAccountById(@PathVariable Long employeeId) throws RecordNotFoundException {
        return employeeRepository.findById(employeeId).map(employee -> {
            return new ResponseEntity<Employee>(employee, new HttpHeaders(), HttpStatus.OK);
        }).orElseThrow(() -> new RecordNotFoundException("Employee id: " + employeeId + " not found!"));
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping
    Employee updateEmployee(@RequestBody Employee employeeRequest) throws RecordNotFoundException {
        return employeeRepository.findById(employeeRequest.getId()).map(employee -> {
            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastName(employeeRequest.getLastName());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RecordNotFoundException("Employee id: " + employeeRequest.getId() + " not found!"));
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId) throws RecordNotFoundException {
        return employeeRepository.findById(employeeId).map(employee -> {
            employeeRepository.delete(employee);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new RecordNotFoundException("Employee id: " + employeeId + " not found!"));
    }
}

