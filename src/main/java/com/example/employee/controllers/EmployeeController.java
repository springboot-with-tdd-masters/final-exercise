package com.example.employee.controllers;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Page<Employee> getAll(Pageable pageRequest) {
        return employeeService.findAll(pageRequest);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {

        final Employee employee = employeeService.findById(id);

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody EmployeeRequest employeeRequest) {

        final Employee employee = employeeService.create(employeeRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<Employee> update(@Valid @RequestBody EmployeeRequest employeeRequest, @PathVariable Long id) {

        final Employee employee = employeeService.update(id, employeeRequest);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        employeeService.delete(id);

        return ResponseEntity.ok("Employee successfully deleted");
    }
}
