package com.example.finalexercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.finalexercise.exception.SkillsTrackerAppException;
import com.example.finalexercise.exception.SkillsTrackerAppExceptionCode;
import com.example.finalexercise.model.Employees;
import com.example.finalexercise.model.Skills;
import com.example.finalexercise.repository.EmployeesRepository;
import com.example.finalexercise.repository.SkillsRepository;
import com.example.finalexercise.request.EmployeesRequest;
import com.example.finalexercise.response.EmployeesResponse;
import com.example.finalexercise.response.SkillsResponse;
import com.example.finalexercise.service.impl.SkillsTrackerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SkillsTrackerServiceTest {
	
	@InjectMocks
	SkillsTrackerServiceImpl service;
	
	@Mock
	EmployeesRepository employeesRepository;
	
	@Mock
	SkillsRepository skillsRepository;
	
	@Test
	@DisplayName("Save - should save an Employee")
	public void shouldSaveAnEmployee() {
		
		EmployeesRequest employeeRequest = new EmployeesRequest();
		employeeRequest.setFirstName("Gerald");
		employeeRequest.setLastName("Estrada");
		
		EmployeesResponse response = new EmployeesResponse();
		response.setFirstName("Gerald");
		response.setLastName("Estrada");
		response.setCreatedAt(LocalDate.now().toString());
		response.setUpdatedAt(LocalDate.now().toString());
		response.setId(1L);
		
		Employees employeeEntity = new Employees();
		employeeEntity.setId(1L);
		employeeEntity.setFirstName("Gerald");
		employeeEntity.setLastName("Estrada");
		employeeEntity.setCreatedDate(new Date());
		employeeEntity.setLastModifiedDate(new Date());
		
		Mockito.when(employeesRepository.save(Mockito.any())).thenReturn(employeeEntity);
		
		EmployeesResponse actualResponse = service.createOrUpdateEmployee(employeeRequest);
		
		assertEquals(response.getFirstName(), actualResponse.getFirstName());
	}
	
	@Test
	@DisplayName("Save - should save an Existing Employee")
	public void shouldSaveAnExistingEmployee() {
		
		EmployeesRequest employeeRequest = new EmployeesRequest();
		employeeRequest.setId(1L);
		employeeRequest.setFirstName("Gerald");
		employeeRequest.setLastName("Daniel");
		
		EmployeesResponse response = new EmployeesResponse();
		response.setFirstName("Gerald");
		response.setLastName("Daniel");
		response.setCreatedAt(LocalDate.now().toString());
		response.setUpdatedAt(LocalDate.now().toString());
		response.setId(1L);
		
		Optional<Employees> employeeEntity = Optional.ofNullable(new Employees());
		Employees newEmployee = employeeEntity.get();
		newEmployee.setId(1L);
		newEmployee.setFirstName("Gerald");
		newEmployee.setLastName("Estrada");
		newEmployee.setCreatedDate(new Date());
		newEmployee.setLastModifiedDate(new Date());
		
		Mockito.when(employeesRepository.findById(Mockito.any())).thenReturn(employeeEntity);
		Mockito.when(employeesRepository.save(Mockito.any())).thenReturn(newEmployee);
		
		EmployeesResponse actualResponse = service.createOrUpdateEmployee(employeeRequest);
		
		assertEquals(response.getFirstName(), actualResponse.getFirstName());
	}
	
	@Test
	@DisplayName("Save - should save an Employee with ID in request")
	public void shouldSaveAnEmployeeWithId() {
		
		EmployeesRequest employeeRequest = new EmployeesRequest();
		employeeRequest.setId(1L);
		employeeRequest.setFirstName("Gerald");
		employeeRequest.setLastName("Daniel");
		
		EmployeesResponse response = new EmployeesResponse();
		response.setFirstName("Gerald");
		response.setLastName("Daniel");
		response.setCreatedAt(LocalDate.now().toString());
		response.setUpdatedAt(LocalDate.now().toString());
		response.setId(1L);
		
		Optional<Employees> employeeEntity = Optional.ofNullable(new Employees());
		Employees newEmployee = employeeEntity.get();
		newEmployee.setId(1L);
		newEmployee.setFirstName("Gerald");
		newEmployee.setLastName("Estrada");
		newEmployee.setCreatedDate(new Date());
		newEmployee.setLastModifiedDate(new Date());
		
		Mockito.when(employeesRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(employeesRepository.save(Mockito.any())).thenReturn(newEmployee);
		
		EmployeesResponse actualResponse = service.createOrUpdateEmployee(employeeRequest);
		
		assertEquals(response.getFirstName(), actualResponse.getFirstName());
	}
	
	@Test
	@DisplayName("Retrieve - should retrieve Skill of an Employee")
	public void shouldRetrieveASkill() {
		
		Optional<Employees> employeeEntity = Optional.ofNullable(new Employees());
		employeeEntity.get().setId(1L);
		employeeEntity.get().setFirstName("Gerald");
		employeeEntity.get().setLastName("Estrada");
		employeeEntity.get().setCreatedDate(new Date());
		employeeEntity.get().setLastModifiedDate(new Date());
		
		Skills skill1 = new Skills();
		skill1.setId(1L);
		skill1.setDuration(1);
		skill1.setDescription("Java");
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployees(employeeEntity.get());
		
		Skills skill2 = new Skills();
		skill2.setId(2L);
		skill2.setDuration(2);
		skill2.setDescription("React");
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployees(employeeEntity.get());
		
		Page<Skills> pageResp = new PageImpl<>(List.of(skill1, skill2));
		
		Pageable page = PageRequest.of(0, 5);
		
		Mockito.when(employeesRepository.findById(Mockito.any())).thenReturn(employeeEntity);
		Mockito.when(skillsRepository.findByEmployeesId(Mockito.any(), Mockito.any())).thenReturn(pageResp);
		
		Page<SkillsResponse> actualResponse = service.getSkillsByEmployeeId(1L, page);
		
		assertEquals(1, actualResponse.getTotalPages());
		assertEquals(2, actualResponse.getSize());
	}
	
	@Test
	@DisplayName("Error - should throw 404 error when invoking unsaved Employee ID")
	public void shouldThrow404Error() {
		Pageable page = PageRequest.of(0, 5);
		
		Mockito.when(employeesRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		
		SkillsTrackerAppException exception = assertThrows(SkillsTrackerAppException.class, () -> service.getSkillsByEmployeeId(1L, page));
        assertEquals(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION.getMessage(), exception.getMessage());
	}
}
