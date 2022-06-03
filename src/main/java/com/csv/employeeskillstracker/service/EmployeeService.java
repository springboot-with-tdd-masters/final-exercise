package com.csv.employeeskillstracker.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Employee;

public interface EmployeeService {

	public Employee createUpdate(Employee employee);
	
	public Employee getEmployeeById(Long id) throws RecordNotFoundException;
	
	public Page<Employee> getAllEmployees(Pageable pageable);
	
	public void deleteEmployee(Long id) throws RecordNotFoundException;
}
