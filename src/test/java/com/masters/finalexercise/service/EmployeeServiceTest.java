package com.masters.finalexercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeService employeeService = new EmployeeServiceImpl();
	
	@InjectMocks
	private SkillService skillService = new SkillServiceImpl();
	
	@Test
	void testCreateEmployee() throws Exception {
		
		Employee employee = new Employee();
		employee.setFirstname("dummyfname");
		employee.setLastname("dummyLname");
		
		Employee savedEmployee = new Employee();
		savedEmployee.setId(1L);
		savedEmployee.setFirstname("dummyfname");
		savedEmployee.setLastname("dummylname");
		
		when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
		Employee emp = employeeService.save(employee);
		
		assertEquals(1L, emp.getId());
		assertEquals("dummyfname", emp.getFirstname());
		assertEquals("dummylname", emp.getLastname());
		
	}
	
	@Test
	void testFindAllEmployee() throws Exception {
		
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee1 = new Employee();
		employee1.setFirstname("dummyfname1");
		employee1.setLastname("dummyLname1");
		
		Employee employee2 = new Employee();
		employee2.setFirstname("dummyfname2");
		employee2.setLastname("dummyLname2");
		
		employees.add(employee1);
		employees.add(employee2);
		
		final Page<Employee> employeesPage = new PageImpl<>(employees);
		when(employeeService.findAll(0, 10, "id")).thenReturn(employeesPage);
	
		Page<Employee> emps = employeeService.findAll(0, 10, "id");
		
		assertEquals(2, emps.getSize());
		assertEquals(1, emps.getTotalPages());
	}
	
	@Test
	void testFindEmployeeById() throws Exception {
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname("dummyfname");
		employee.setLastname("dummylname");
		
		Optional<Employee> emp = Optional.of(employee);
		
		when(employeeRepository.findById(1L)).thenReturn(emp);
		
		Employee empObj = employeeService.findById(1L);
		
		assertEquals(1L, empObj.getId());
		assertEquals("dummyfname", empObj.getFirstname());
		assertEquals("dummylname", empObj.getLastname());
	}
	
	@Test
	void testDeleteEmployeeById() throws Exception {
		
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname("dummyfname");
		employee.setLastname("dummylname");
		
		Optional<Employee> emp = Optional.of(employee);
		when(employeeRepository.findById(1L)).thenReturn(emp);
		
		Employee empObj = employeeService.delete(1L);
		
		assertEquals(1L, empObj.getId());
		assertEquals("dummyfname", empObj.getFirstname());
		assertEquals("dummylname", empObj.getLastname());
	}
	
}
