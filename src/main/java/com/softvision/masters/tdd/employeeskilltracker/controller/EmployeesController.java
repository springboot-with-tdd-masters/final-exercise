package com.softvision.masters.tdd.employeeskilltracker.controller;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.service.EmployeeService;
import com.softvision.masters.tdd.employeeskilltracker.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    SkillService skillService;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.create(employee), new HttpHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<Page<Employee>> getAll(@PageableDefault Pageable page) {
        return new ResponseEntity<>(employeeService.getAll(page), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getContainingName(@RequestParam(required=false) String name,
                                                          @PageableDefault Pageable page) {
        if (name == null) {
            return getAll(page);
        }
        return new ResponseEntity<>(employeeService.getNameContaining(name, page), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable long id) {
        return new ResponseEntity<>(employeeService.getById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}/skills")
    public ResponseEntity<Page<Skill>> getAllSkillsById(@PathVariable long id, @PageableDefault Pageable page) {
        return new ResponseEntity<>(skillService.getAllByEmployee(id, page), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{id}/skills")
    public ResponseEntity<Skill> createSkill(@PathVariable long id, @RequestBody Skill skill) {
        Skill savedSkill = employeeService.createSkill(id, skill);
        return new ResponseEntity<>(savedSkill, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        employeeService.delete(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }
}
