package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.dto.EmployeeRequest;
import com.example.demo.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping()
    public ResponseEntity<Employee> createUpdateEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        if(Objects.isNull(employeeRequest.getId())){
            return ResponseEntity.ok(employeeServiceImpl.createNewEmployee(employeeRequest));
        }
        return ResponseEntity.ok(employeeServiceImpl.updateEmployee(employeeRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> readEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeServiceImpl.readEmployeeById(id));
    }

    @GetMapping()
    private ResponseEntity<Page<Employee>> readAllEmployees(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                        Pageable pageable) {
        return ResponseEntity.ok(employeeServiceImpl.readAllEmployees(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeServiceImpl.deleteEmployeeById(id);
        return ResponseEntity.ok().build();
    }
}
