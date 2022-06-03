package com.java.finalexercise.controller;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeControlller {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    public ResponseEntity<Employee> save(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody Employee employee)
            throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.update(employee), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id)
            throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.delete(id), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id)
            throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> find(@PageableDefault(page = 0, size = 20)
                                             @SortDefault.SortDefaults({
                                                     @SortDefault(sort = "firstName",
                                                             direction = Sort.Direction.DESC)
                                             })
                                                     Pageable pageable)
            throws EmployeeNotFoundException {
        return new ResponseEntity<>(employeeService.find(pageable), HttpStatus.OK);
    }
}
