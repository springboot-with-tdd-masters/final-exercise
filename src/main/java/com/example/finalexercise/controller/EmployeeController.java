package com.example.finalexercise.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;
import com.example.finalexercise.service.EmployeeService;

@RequestMapping("/employees")
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public EmployeeDto addEmployee(@RequestBody EmployeeRequest request) {
		if (request.getId() == null) {
			return employeeService.saveEmployee(request);
		}
		return employeeService.updateEmployee(request);
	}
	
	@GetMapping("/{id}")
	public EmployeeDto getEmployee(@PathVariable Long id) {
		return employeeService.getEmployee(id);
	}
	
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}

}
