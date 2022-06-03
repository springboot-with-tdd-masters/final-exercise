package com.java.master.finals.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.master.finals.exception.RecordNotFoundException;
import com.java.master.finals.model.Employee;
import com.java.master.finals.repository.EmployeeRepository;
import com.java.master.finals.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Override
	public Employee createUpdateEmployee(Employee employeeRequest) {
		Optional<Employee> employee = employeeRepo.findById(employeeRequest.getId());

		if (employee.isPresent()) {
			Employee updateEmployee = employee.get();
			updateEmployee.setFirstName(employeeRequest.getFirstName());
			updateEmployee.setLastName(updateEmployee.getLastName());
			return employeeRepo.save(updateEmployee);
		} else {
			return employeeRepo.save(employeeRequest);
		}

	}

	@Override
	public Page<Employee> getAll(Pageable page) {
		return employeeRepo.findAll(page);
	}

	@Override
	public Employee getById(Long id) {
		Optional<Employee> employee = employeeRepo.findById(id);
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("Record Not Found!");
		}
	}

	@Override
	public void delete(Long id) {

		Optional<Employee> employee = employeeRepo.findById(id);
		if(employee.isPresent()) {
			employeeRepo.delete(employee.get());
		} else {
			throw new RecordNotFoundException("Record Not Found!");
		}
	}

}
