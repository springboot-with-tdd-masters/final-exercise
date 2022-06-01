package com.example.finalexercise.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.finalexercise.model.Employee;

@EnableJpaAuditing
@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	@DisplayName("Should save Employee entity")
	public void shouldSaveEmployee() {
	     Employee employee = new Employee();
	     employee.setFirstname("Christian");
	     employee.setLastname("Perez");
	     
	     Employee savedEmployee = employeeRepository.save(employee);

	     assertEquals(employee.getFirstname(), savedEmployee.getFirstname());
	     assertNotNull(savedEmployee.getCreatedDate());
	     assertNotNull(savedEmployee.getId());
	}
	
	@Test
	@DisplayName("Should read Employee entity")
	public void shouldGetEmployee() {
	     Employee employee = new Employee();
	     employee.setFirstname("Christian");
	     employee.setLastname("Perez");
	     
	     Employee savedEmployee = employeeRepository.save(employee);

	     Employee readEmployee = employeeRepository.getById(savedEmployee.getId());
	     
	     assertEquals(savedEmployee, readEmployee);
	}

	@Test
	@DisplayName("Should update Employee entity")
	public void shouldUpdateEmployee() {
	     Employee employee = new Employee();
	     employee.setFirstname("Christian");
	     employee.setLastname("Perez");
	     
	     Employee savedEmployee = employeeRepository.save(employee);

	     Employee readEmployee = employeeRepository.getById(savedEmployee.getId());
	     readEmployee.setFirstname("Ian");
	     
	     Employee updatedEmployee = employeeRepository.save(readEmployee);

	     assertEquals("Ian", updatedEmployee.getFirstname());
	}

	@Test
	@DisplayName("Should delete Employee entity")
	public void shouldDeleteEmployee() {
	     Employee employee = new Employee();
	     employee.setFirstname("Christian");
	     employee.setLastname("Perez");
	     
	     Employee savedEmployee = employeeRepository.save(employee);
	     
	     employeeRepository.delete(savedEmployee);
	     
	     Optional employeeOpt =  employeeRepository.findById(savedEmployee.getId());
	   
	     assertTrue(employeeOpt.isEmpty());
	}
	
}
