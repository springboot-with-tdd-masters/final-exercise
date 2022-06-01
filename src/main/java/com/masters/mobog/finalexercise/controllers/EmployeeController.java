package com.masters.mobog.finalexercise.controllers;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.services.EmployeeService;
import com.masters.mobog.finalexercise.services.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    Employee getEmployeeById(Long id){
        return this.employeeService.findById(id);
    }
}
