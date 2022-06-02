package com.example.finalexercise.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.finalexercise.exception.EmployeeNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;
import com.example.finalexercise.repository.EmployeeRepository;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public EmployeeDto saveEmployee(EmployeeRequest employeeRequest) {
		Employee employee = new Employee();
		employee.setFirstname(employeeRequest.getFirstname());
		employee.setLastname(employeeRequest.getLastname());
		
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeDto.convertToDto(savedEmployee);
	}

	@Override
	public EmployeeDto getEmployee(Long l) {
		return employeeRepository.findById(1L)
				.map(EmployeeDto::convertToDto)
				.orElseThrow(EmployeeNotFoundException::new);
	}

	@Override
	public EmployeeDto updateEmployee(EmployeeRequest request) {
		Employee employee = findEmployee(request.getId());
		employee.setFirstname(request.getFirstname());
		employee.setLastname(request.getLastname());

		return EmployeeDto.convertToDto(employee);
	}

	@Override
	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);

		employeeRepository.delete(employee);		
	}

	@Override
	public Employee findEmployee(Long id) {
		return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
	}

	@Override
	public Page<EmployeeDto> getAllEmployees(Pageable pageable) {
		return employeeRepository.findAll(pageable).map(EmployeeDto::convertToDto);
	}

}
