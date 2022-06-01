package com.masters.mobog.finalexercise.controllers;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
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
    @PostMapping("{id}/skills")
    Skill addSkillToEmployee(@PathVariable Long id, @RequestBody EmployeeSkillRequest request){
        return this.skillService.addSkillToEmployee(id, request);
    }
    @PutMapping("{id}/skills/{skillId}")
    Skill addSkillToEmployee(@PathVariable Long id, @PathVariable Long skillId, @RequestBody EmployeeSkillRequest request){
        return this.skillService.updateEmployeeSkill(id, skillId, request);
    }
    @GetMapping("{id}/skills")
    Page<Skill> getEmployeSkillsWithPage(@PathVariable Long id, Pageable page){
        return this.skillService.getAllEmployeeSkills(id, page);
    }
    @DeleteMapping("{id}/skills/{skillId}")
    void deleteEmployeeSkills(@PathVariable Long id, @PathVariable Long skillId){

        this.skillService.deleteEmployeeSkill(id, skillId);
    }
}
