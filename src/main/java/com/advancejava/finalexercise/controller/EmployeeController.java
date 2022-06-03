package com.advancejava.finalexercise.controller;

import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.model.dto.DTOResponse;
import com.advancejava.finalexercise.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    final static Logger logger = Logger.getLogger("EmployeeController");

    @Autowired
    private EmployeeService service;

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        logger.info("adding employee...");
        return new ResponseEntity<>(service.addEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id){
        logger.info(String.format("get id %d", id));
        return service.getEmployee(id);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        logger.info("get all employees...");
        return service.getAllEmployees();
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee request){
        logger.info(String.format("updating id %d", request.getId()));

        return service.updateEmployee(request);
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        logger.info(String.format("deleting id %d", id));
        return service.deleteEmployee(id);
    }

    @GetMapping("/employees/page/{page}/size/{size}")
    private DTOResponse<Page<Employee>> getEmployeesWithPaginationAndSort(
            @PathVariable int page, @PathVariable int size,
            @RequestParam String field, @RequestParam String order) {
        logger.info("Employees Page "+page+1);
        Page<Employee> employees = service.getWithPaginationAndSort(page, size, field,order);
        return new DTOResponse<>(employees.getSize(), employees);
    }


}
