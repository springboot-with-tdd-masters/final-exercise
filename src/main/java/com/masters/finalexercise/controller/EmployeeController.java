package com.masters.finalexercise.controller;

import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;
import com.masters.finalexercise.service.EmployeeService;
import com.masters.finalexercise.service.SkillService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private SkillService skillService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve All Employees", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<Employee>> findAllEmployees(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) throws RecordNotFoundException {

        Page<Employee> employees = employeeService.findAll(pageNo, pageSize, sortBy);
        return new ResponseEntity<Page<Employee>>(employees, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve Employee By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) throws RecordNotFoundException {
        Employee employee = employeeService.findById(id);
        return new ResponseEntity<Employee>(employee, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Save an employee", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Employee> save(@RequestBody Employee entity) throws RecordNotFoundException {
        Employee savedEmployee= employeeService.save(entity);
        return new ResponseEntity<Employee>(savedEmployee, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Employee By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Employee> deleteEmployeeById(@PathVariable Long id) throws RecordNotFoundException {
        Employee employee = employeeService.delete(id);
        return new ResponseEntity<Employee>(employee, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/{employeeId}/skills")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve All Skills", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Page<Skill>> findAllSkills(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) throws RecordNotFoundException {

        Page<Skill> skills = skillService.findAll(employeeId, pageNo, pageSize, sortBy);
        return new ResponseEntity<Page<Skill>>(skills, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{employeeId}/skills/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve Skill By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Skill> findSkillById(@PathVariable Long employeeId, @PathVariable Long id) throws RecordNotFoundException {
        Skill skill = skillService.findById(employeeId, id);
        return new ResponseEntity<Skill>(skill, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{employeeId}/skills")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Save a Skill", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Skill> save(@PathVariable Long employeeId, @RequestBody Skill skill) throws RecordNotFoundException {
    	Skill savedSkill = skillService.save(skill, employeeId);
        return new ResponseEntity<Skill>(savedSkill, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/skills/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Skill By Id", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Skill> deleteBookById(@PathVariable Long id) throws RecordNotFoundException {
        Skill skill = skillService.delete(id);
        return new ResponseEntity<Skill>(skill, new HttpHeaders(), HttpStatus.OK);
    }

}
