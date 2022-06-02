package com.example.finalexercise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;

public interface EmployeeService {
	
	EmployeeDto saveEmployee(EmployeeRequest employeeRequest);
	EmployeeDto getEmployee(Long id);
	Employee findEmployee(Long id);
	EmployeeDto updateEmployee(EmployeeRequest request);
	void deleteEmployee(Long id);
	Page<EmployeeDto> getAllEmployees(Pageable pageable);
}
