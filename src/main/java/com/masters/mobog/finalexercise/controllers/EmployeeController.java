package com.masters.mobog.finalexercise.controllers;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.services.EmployeeService;
import com.masters.mobog.finalexercise.services.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SkillService skillService;
    public EmployeeController(EmployeeService employeeService, SkillService skillService){
        this.skillService = skillService;
        this.employeeService = employeeService;
    }

    @PostMapping
    Employee createEmployee(EmployeeRequest request){
        return this.employeeService.createEmployee(request);
    }
    @GetMapping("/{id}")
    Employee getEmployeeById(@PathVariable Long id){
        return this.employeeService.findById(id);
    }
    @GetMapping
    Page<Employee> getWithPage(Pageable page){
        return this.employeeService.findAll(page);
    }
    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id){
        this.employeeService.delete(id);
    }
    @PutMapping("/{id}")
    Employee updateEmployee(@PathVariable Long id, EmployeeRequest request){
       return this.employeeService.update(id, request);
    }
}
