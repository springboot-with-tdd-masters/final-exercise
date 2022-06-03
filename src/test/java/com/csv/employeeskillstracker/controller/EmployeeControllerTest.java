package com.csv.employeeskillstracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.csv.employeeskillstracker.exception.BadRequestException;
import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.service.EmployeeService;
import com.csv.employeeskillstracker.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	static final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	@DisplayName("Should Create Employee")
	public void should_Create_Employee() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken("Charles", "password");
		Employee employee = new Employee("Charles", "Alcachupas");

		when(employeeService.createUpdate(any())).thenReturn(employee);

		mockMvc.perform(post("/employees").header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("firstname").value("Charles"))
				.andExpect(jsonPath("lastname").value("Alcachupas"));
		verify(employeeService, atMostOnce()).createUpdate(any());
	}
	
	@Test
	@DisplayName("Should throw bad request exception when employee data is null")
	void test_Throw_BadRequestException() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken("Charles", "password");
		when(employeeService.createUpdate(any())).thenThrow(BadRequestException.class);
		mockMvc.perform(post("/employees").header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(null)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
		
		verify(employeeService, atMostOnce()).createUpdate(any());
	}
	
	@Test
	@DisplayName("Should give http status 401 (unauthorized) when invalid token was supplied")
	void test_Fail_unauthorized() throws Exception {
		String accessToken = "ABC123";
		
		Employee employee = new Employee("Charles", "Alcachupas");
		when(employeeService.createUpdate(any())).thenReturn(employee);

		mockMvc.perform(post("/employees")
				.content(objectMapper.writeValueAsString(employee))
				.header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}
}
