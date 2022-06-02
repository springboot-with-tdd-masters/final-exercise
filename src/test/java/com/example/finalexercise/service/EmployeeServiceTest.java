package com.example.finalexercise.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.finalexercise.exception.EmployeeNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;
import com.example.finalexercise.repository.EmployeeRepository;

public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Should save Employee and return details")
	public void shouldSaveEmployee() {
		EmployeeRequest empRequest = new EmployeeRequest("Christian", "Perez");

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname(empRequest.getFirstname());
		employee.setLastname(empRequest.getLastname());
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		EmployeeDto expectedReponse = EmployeeDto.convertToDto(employee);

		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		EmployeeDto actualResponse = employeeService.saveEmployee(empRequest);

		assertEquals(expectedReponse, actualResponse);
	}
	
	@Test
	@DisplayName("Should read Employee and return details")
	public void shouldReadEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname("Christian");
		employee.setLastname("Perez");
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		EmployeeDto expectedReponse = EmployeeDto.convertToDto(employee);

		Optional<Employee> employeeOpt = Optional.of(employee);
		when(employeeRepository.findById(1L)).thenReturn(employeeOpt);

		EmployeeDto actualResponse = employeeService.getEmployee(1L);

		assertEquals(expectedReponse, actualResponse);
	}
	
	@Test
	@DisplayName("Should update Employee and return details")
	public void shouldUpdateEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname("Christian");
		employee.setLastname("Perez");
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		Optional<Employee> employeeOpt = Optional.of(employee);
		when(employeeRepository.findById(1L)).thenReturn(employeeOpt);

		EmployeeRequest empRequest = new EmployeeRequest(1L, "Ian", "Perez");
		EmployeeDto actualResponse = employeeService.updateEmployee(empRequest);

		assertEquals("Ian", actualResponse.getFirstname());
	}
	
	@Test
	@DisplayName("Should get all employees with paging and sorting")
	public void getAllSkills() {
		Employee emp = new Employee();
	    emp.setId(1L);
	    emp.setFirstname("Christian");
	
		Employee emp2 = new Employee();
	    emp2.setId(2L);
	    emp2.setFirstname("Juan");
	
	    Page<Employee> pagedEmployees = new PageImpl(Arrays.asList(emp2, emp));
	    
	 	Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "firstname"));
	    when(employeeRepository.findAll(pageable))
	    	.thenReturn(pagedEmployees);

	    Page<EmployeeDto> actualResponse = employeeService.getAllEmployees(pageable);
	    
	    assertAll(
	    		() -> assertEquals(2, actualResponse.getNumberOfElements()),
			    () -> assertEquals(2L, actualResponse.getContent().get(0).getId()),
			    () -> assertEquals("Juan", actualResponse.getContent().get(0).getFirstname()),
			    () -> assertEquals(1L, actualResponse.getContent().get(1).getId()),
			    () -> assertEquals("Christian", actualResponse.getContent().get(1).getFirstname())
		    );	
	}
	
	@Test
	@DisplayName("Should delete Employee")
	public void shouldDeleteEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname("Christian");
		employee.setLastname("Perez");
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		Optional<Employee> employeeOpt = Optional.of(employee);
		when(employeeRepository.findById(1L)).thenReturn(employeeOpt);
		
		employeeService.deleteEmployee(1L);
		
		verify(employeeRepository).delete(employee);
	}
	

	@Test
	@DisplayName("Should throw EmployeeNotFoundException when no employee record")
	public void shouldThrowException() {		
		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee(1L));
	}
}
