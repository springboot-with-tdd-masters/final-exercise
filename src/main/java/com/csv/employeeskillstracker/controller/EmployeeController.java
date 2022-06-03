package com.csv.employeeskillstracker.controller;

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

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.service.EmployeeService;


@RestController
@RequestMapping("employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping()
	public Page<Employee> getAllEmployee(Pageable page) throws RecordNotFoundException {
		return employeeService.getAllEmployees(page);
	}
	
	@GetMapping("/{employee_id}")
	public Employee getEmployeeById(@PathVariable("employee_id") Long id) throws RecordNotFoundException {
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping()
	public Employee createOrUpdateEmployee(@RequestBody Employee employee) {
		return employeeService.createUpdate(employee);
	}
	
	@DeleteMapping("/{employee_id}")
	public void deleteEmployee(@PathVariable("employee_id") Long id) throws RecordNotFoundException {
		employeeService.deleteEmployee(id);
	}
	
}
