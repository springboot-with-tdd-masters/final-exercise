package com.java.master.finals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.master.finals.model.Employee;
import com.java.master.finals.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public Employee createUpdate(@RequestBody Employee employee) {
		return employeeService.createUpdateEmployee(employee);
	}
	
	@GetMapping
	public Page<Employee> getAll(Pageable page) {
		return employeeService.getAll(page);
		
	}
	
	@GetMapping("/{id}")
	public Employee getById(@PathVariable Long id) {
		return employeeService.getById(id);
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		employeeService.delete(id);
	}

}
