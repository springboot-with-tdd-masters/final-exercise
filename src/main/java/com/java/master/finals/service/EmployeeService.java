package com.java.master.finals.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.master.finals.model.Employee;

public interface EmployeeService {
	
	Employee createUpdateEmployee(Employee employee);
	
	Page<Employee> getAll(Pageable page);
	
	Employee getById(Long id);
	
	void delete(Long id);

}
