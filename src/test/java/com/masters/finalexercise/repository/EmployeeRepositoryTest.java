package com.masters.finalexercise.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.masters.finalexercise.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	public void testSaveEmployee() {
		
	     Employee employee = new Employee();
	     employee.setFirstname("dummyfname");
	     employee.setLastname("dummylname");
	     
	     Employee savedEmployee = employeeRepository.save(employee);

	     assertEquals(employee.getId(), 1L);
	     assertEquals(employee.getFirstname(), savedEmployee.getFirstname());
	     assertNotNull(savedEmployee.getId());
	}
	
	@Test
	public void findAllEmployee() {
		
	     Employee employee1 = new Employee();
	     employee1.setFirstname("dummyfname1");
	     employee1.setLastname("dummylname1");
	     
	     Employee employee2 = new Employee();
	     employee2.setFirstname("dummyfname2");
	     employee2.setLastname("dummylname2");
	     
	     Employee savedEmployee1 = employeeRepository.save(employee1);
	     Employee savedEmployee2 = employeeRepository.save(employee1);
	     
	     Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
	     Page<Employee> employees = employeeRepository.findAll(paging);

	     assertNotNull(employees);
	     assertEquals(employees.getSize(), 10);
	    
	}
	
	@Test
	public void findEmployeeById() {
		
	     Employee employee1 = new Employee();
	     employee1.setFirstname("dummyfname1");
	     employee1.setLastname("dummylname1");
	     
	     Employee savedEmployee1 = employeeRepository.save(employee1);
	     
	     Optional<Employee> employee = employeeRepository.findById(1L);

	     assertNotNull(employee);
	}
	
}
