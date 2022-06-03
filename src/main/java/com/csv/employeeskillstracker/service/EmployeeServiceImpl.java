package com.csv.employeeskillstracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Employee createUpdate(Employee employeeEntity) {
		Optional<Employee> employee = employeeRepository.findById(employeeEntity.getId());
		
		if(employee.isPresent()) {
			Employee newEmployee = employee.get();
			newEmployee.setFirstname(employeeEntity.getFirstname());
			newEmployee.setLastname(employeeEntity.getLastname());
			
			return employeeRepository.save(newEmployee);
		} else 
			return employeeRepository.save(employeeEntity);
		
	}

	@Override
	public Employee getEmployeeById(Long id) throws RecordNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(id);
		employee.orElseThrow(RecordNotFoundException::new);
		return employee.get();
	}

	@Override
	public Page<Employee> getAllEmployees(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	@Override
	public void deleteEmployee(Long id) throws RecordNotFoundException {
		employeeRepository.findById(id).orElseThrow(RecordNotFoundException::new);
		employeeRepository.deleteById(id);
		
	}

}
