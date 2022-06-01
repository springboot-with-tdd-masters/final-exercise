package com.example.finalexercise.service;

import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;

public interface EmployeeService {
	
	EmployeeDto saveEmployee(EmployeeRequest employeeRequest);
	EmployeeDto getEmployee(Long l);
	EmployeeDto updateEmployee(EmployeeRequest request);
	void deleteEmployee(Long id);

}
