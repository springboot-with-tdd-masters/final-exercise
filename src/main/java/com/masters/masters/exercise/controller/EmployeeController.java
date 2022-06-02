package com.masters.masters.exercise.controller;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.EmployeeDto;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.services.EmployeesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeesServiceImpl service;

    @PostMapping
    public ResponseEntity<Employees> saveOrUpdateEmployee(@RequestBody EmployeeDto request){
        Employees response = service.createOrUpdateEmployee(request);
        return new ResponseEntity<Employees>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> findById(@PathVariable Long id) throws RecordNotFoundException {
        Employees response = service.findById(id);
        return new ResponseEntity<Employees>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Employees>> findAllEmployees(Pageable pageRequest) {
        Page<Employees>list = service.findAllEmployees(pageRequest);
        return new ResponseEntity<Page<Employees>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Long id) throws RecordNotFoundException {
         service.deleteEmployee(id);
        return new ResponseEntity(new HttpHeaders(), HttpStatus.OK);
    }
}
