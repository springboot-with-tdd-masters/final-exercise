package com.example.finalexercise.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.finalexercise.model.Employees;
import com.example.finalexercise.request.EmployeesRequest;
import com.example.finalexercise.response.EmployeesResponse;
import com.example.finalexercise.response.SkillsResponse;
import com.example.finalexercise.service.SkillsTrackerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SkillsTrackerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	SkillsTrackerService service;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	@DisplayName("Save - should save employee")
	public void shouldSaveAnEmployee() throws JsonProcessingException, Exception {
		EmployeesResponse employee = new EmployeesResponse();
		employee.setFirstName("Gerald");
		employee.setLastName("Estrada");
		employee.setCreatedAt("Fri Jun 03 15:16:01 CST 2022");
		employee.setUpdatedAt("Fri Jun 03 15:16:01 CST 2022");
		employee.setId(1L);
		
		EmployeesRequest newEmployee = new EmployeesRequest();
		newEmployee.setFirstName("Gerald");
		newEmployee.setLastName("Estrada");
		
		Mockito.when(service.createOrUpdateEmployee(ArgumentMatchers.any(EmployeesRequest.class))).thenReturn(employee);
		
		this.mockMvc
		.perform(post("/employees").content(mapper.writeValueAsString(newEmployee))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Gerald"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Estrada"));
		
	}
	
	@Test
	@DisplayName("Retrieve - should retrieve all skills")
	public void shouldRetrieveSkills() throws Exception {
		Employees employee = new Employees();
		employee.setId(1L);
		
		SkillsResponse skill1 = new SkillsResponse();
		skill1.setId(1L);
		skill1.setDuration(1);
		skill1.setDescription("Java");
		skill1.setLastUsed(LocalDate.now().toString());
		skill1.setEmployeeId(employee.getId());
		
		SkillsResponse skill2 = new SkillsResponse();
		skill2.setId(2L);
		skill2.setDuration(2);
		skill2.setDescription("React");
		skill2.setLastUsed(LocalDate.now().toString());
		skill2.setEmployeeId(employee.getId());
		
    	Page<SkillsResponse> pageResp = new PageImpl<>(List.of(skill1, skill2));
    	
		when(service.getSkillsByEmployeeId(Mockito.any(), Mockito.any())).thenReturn(pageResp);

		this.mockMvc.perform(get("/employees/1/skills?page=0&size=2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(2));
		
		Mockito.verify(service, times(1)).getSkillsByEmployeeId(Mockito.any(), Mockito.any());
	}

}
