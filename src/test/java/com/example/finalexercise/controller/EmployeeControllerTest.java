package com.example.finalexercise.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.finalexercise.exception.EmployeeNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.dto.EmployeeDto;
import com.example.finalexercise.model.dto.EmployeeRequest;
import com.example.finalexercise.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("Should save Employee and return details")
	public void shouldSaveEmployee() throws Exception {
		EmployeeRequest empRequest = new EmployeeRequest("Christian", "Perez");

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname(empRequest.getFirstname());
		employee.setLastname(empRequest.getLastname());
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		EmployeeDto expectedReponse = EmployeeDto.convertToDto(employee);

		when(employeeService.saveEmployee(empRequest)).thenReturn(expectedReponse);
		
		this.mockMvc.perform(post("/employees").content(
            	objectMapper.writeValueAsString(empRequest)
    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(employee.getFirstname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(employee.getLastname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());

		verify(employeeService).saveEmployee(empRequest);
	}
	
	@Test
	@DisplayName("Should be able to get Employee")
	public void shouldBeAbleToGetEmployee() throws Exception {
		EmployeeRequest empRequest = new EmployeeRequest("Christian", "Perez");

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname(empRequest.getFirstname());
		employee.setLastname(empRequest.getLastname());
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		EmployeeDto expectedReponse = EmployeeDto.convertToDto(employee);

		when(employeeService.getEmployee(1L)).thenReturn(expectedReponse);
		
		this.mockMvc.perform(get("/employees/1"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(employee.getFirstname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(employee.getLastname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());

		verify(employeeService).getEmployee(1L);
	}
	
	@Test
	@DisplayName("Should be able to update Employee")
	public void shouldUpdatemployee() throws Exception {
		EmployeeRequest empRequest = new EmployeeRequest(1L, "Christian", "Perez");

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setFirstname(empRequest.getFirstname());
		employee.setLastname(empRequest.getLastname());
		employee.setCreatedDate(new Date());
		employee.setUpdatedDate(employee.getCreatedDate());

		EmployeeDto expectedReponse = EmployeeDto.convertToDto(employee);

		when(employeeService.updateEmployee(empRequest)).thenReturn(expectedReponse);
		
		this.mockMvc.perform(post("/employees").content(
            	objectMapper.writeValueAsString(empRequest)
    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(employee.getFirstname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(employee.getLastname()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.updatedDate").exists());

		verify(employeeService).updateEmployee(empRequest);
	}
	
	@Test
	@DisplayName("Should be able to delete Employee")
	public void shouldDeleteEmployee() throws Exception {
		this.mockMvc.perform(delete("/employees/1"))
			.andExpect(status().isOk());

		verify(employeeService).deleteEmployee(1L);
	}
	
	@Test
	@DisplayName("Should be return a 404 error response when no employee record found")
	public void shouldReturn404CodeWhenNoEmployeeRecordFound() throws Exception {
		when(employeeService.getEmployee(1L)).thenThrow(EmployeeNotFoundException.class);

		this.mockMvc.perform(get("/employees/1"))
			.andExpect(status().isNotFound());

		verify(employeeService).getEmployee(1L);
	}
}
