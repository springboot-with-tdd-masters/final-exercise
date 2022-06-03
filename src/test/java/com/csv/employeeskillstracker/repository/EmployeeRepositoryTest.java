package com.csv.employeeskillstracker.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.csv.employeeskillstracker.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Test
	@DisplayName("Should save employee to DB")
	public void should_Save_Employee() {
		Employee savedEmployee = employeeRepository.save(new Employee("Charles", "Alcachupas"));
		
		assertThat(savedEmployee).extracting("firstname", "lastname").containsExactly("Charles", "Alcachupas");
	}
}
