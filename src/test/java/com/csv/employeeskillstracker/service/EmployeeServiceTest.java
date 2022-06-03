package com.csv.employeeskillstracker.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.csv.employeeskillstracker.exception.BadRequestException;
import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
	@Captor
	ArgumentCaptor<Employee> saveEmployeeCaptor;

	@Test
	@DisplayName("Should create employee")
	public void should_Save_Employee() {
		Employee employee = new Employee("Charles", "Alcachupas");
		
		when(employeeRepository.save(saveEmployeeCaptor.capture())).thenReturn(employee);
		
		Employee employeeActual = employeeService.createUpdate(employee);
		
		assertAll(() -> assertEquals(saveEmployeeCaptor.getValue().getFirstname(), employeeActual.getFirstname()),
				() -> assertEquals(saveEmployeeCaptor.getValue().getLastname(), employeeActual.getLastname()));
		
	}
	
	@Test
	@DisplayName("Should Throw BadRequestException")
	void test_throw_BadRequestException() {
		when(employeeRepository.save(any())).thenThrow(BadRequestException.class);
		assertThrows(BadRequestException.class, ()-> employeeService.createUpdate(new Employee()));
		
	}
	
}
