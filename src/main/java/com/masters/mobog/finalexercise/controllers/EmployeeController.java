package com.masters.mobog.finalexercise.controllers;

import com.masters.mobog.finalexercise.services.EmployeeService;
import com.masters.mobog.finalexercise.services.SkillService;
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
}
