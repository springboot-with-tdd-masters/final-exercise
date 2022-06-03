package com.csv.employeeskillstracker.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.model.Skill;
import com.csv.employeeskillstracker.service.SkillService;
import com.csv.employeeskillstracker.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class SkillControllerTest {
	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	MockMvc mockMvc;

	static final ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private SkillService skillService;

	@Test
	@DisplayName("Should Get All Skills of an Employee")
	public void should_Get_All_Skills() throws Exception {
		Employee employee = new Employee("Charles", "Alcachupas");
		
		Skill skill1 = new Skill();
		skill1.setDescription("Java Springboot");
		skill1.setDuration(2);
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployee(employee);
		
		Skill skill2 = new Skill();
		skill2.setDescription("ReactJs");
		skill2.setDuration(4);
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployee(employee);
		
		List<Skill> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);

		List<Skill> sortedAccounts = skills.stream().sorted(Comparator.comparing(Skill::getDescription))
				.collect(Collectors.toList());
		Page<Skill> skillPage = new PageImpl<>(sortedAccounts);

		Pageable pageable = PageRequest.of(0, 5, Sort.by("description").ascending());
		when(skillService.getAllSkills(pageable, 1L)).thenReturn(skillPage);

		String accessToken = tokenUtil.obtainAccessToken("Charles", "password");
		
		mockMvc.perform(get("/employees/1/skills?page=0&size=5&sort=description,asc").header("Authorization",
				"Bearer " + accessToken)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content.[0].description").value("Java Springboot"))
				.andExpect(jsonPath("$.content.[0].duration").value(2))
				.andExpect(jsonPath("$.content.[0].lastUsed").value(String.valueOf(LocalDate.now())))
				.andExpect(jsonPath("$.content.[1].description").value("ReactJs"))
				.andExpect(jsonPath("$.content.[1].duration").value(4))
				.andExpect(jsonPath("$.content.[1].lastUsed").value(String.valueOf(LocalDate.now())));

		verify(skillService).getAllSkills(pageable, 1L);
	}
	
	@Test
	@DisplayName("Should give http status 401 (unauthorized) when invalid token was supplied")
	void test_Fail_unauthorized() throws Exception {
		Employee employee = new Employee("Charles", "Alcachupas");
		
		Skill skill1 = new Skill();
		skill1.setDescription("Java Springboot");
		skill1.setDuration(2);
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployee(employee);
		
		Skill skill2 = new Skill();
		skill2.setDescription("ReactJs");
		skill2.setDuration(4);
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployee(employee);
		
		List<Skill> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);

		List<Skill> sortedAccounts = skills.stream().sorted(Comparator.comparing(Skill::getDescription))
				.collect(Collectors.toList());
		Page<Skill> skillPage = new PageImpl<>(sortedAccounts);

		Pageable pageable = PageRequest.of(0, 5, Sort.by("description").ascending());
		when(skillService.getAllSkills(pageable, 1L)).thenReturn(skillPage);

		String accessToken = "ABC123";
		
		mockMvc.perform(get("/employees/1/skills?page=0&size=5&sort=description,asc")
				.header("Authorization",
				"Bearer " + accessToken)).andExpect(status().isUnauthorized());
	}
}
