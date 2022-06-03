package com.java.master.finals.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.master.finals.model.Employee;
import com.java.master.finals.service.EmployeeService;
import com.java.master.finals.util.AccessTokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	AccessTokenUtil accessTokenUtil;

	@Autowired
	MockMvc mockMvc;

	static final ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private EmployeeService employeeService;

	@Test
	@DisplayName("Should Create Employee")
	public void should_Create_Employee() throws Exception {
		String accessToken = accessTokenUtil.obtainAccessToken("Jerymy", "password");
		Employee employee = new Employee("Jerymy", "Gomez");

		when(employeeService.createUpdateEmployee(any())).thenReturn(employee);

		mockMvc.perform(post("/employees").header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("firstName").value("Jerymy"))
				.andExpect(jsonPath("lastName").value("Gomez"));
		verify(employeeService, atMostOnce()).createUpdateEmployee(any());
	}
}
