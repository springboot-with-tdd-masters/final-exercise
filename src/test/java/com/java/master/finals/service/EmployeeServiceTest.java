package com.java.master.finals.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.master.finals.model.Employee;
import com.java.master.finals.repository.EmployeeRepository;
import com.java.master.finals.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	@Mock
	private EmployeeRepository employeeRepo;

	
	@Test
	@DisplayName("Should save employee")
	public void shouldSaveEmployee_Test() {

		// arrange
		Employee employee = new Employee("Jerymy", "Gomez");

		// act
		when(employeeRepo.save(any())).thenReturn(employee);
		Employee employeeSave = employeeService.createUpdateEmployee(employee);
		

		// assert
		assertThat(employeeSave).extracting("firstName", "lastName").containsExactly("Jerymy", "Gomez");
	}
	
	@Test
	@DisplayName("Should get skill of an employee")
	public void shouldGellAllSkillsOfEmployee_Test() {
		
		
	}
}
