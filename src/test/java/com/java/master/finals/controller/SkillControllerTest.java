package com.java.master.finals.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.master.finals.model.Employee;
import com.java.master.finals.model.Skill;
import com.java.master.finals.service.SkillService;
import com.java.master.finals.util.AccessTokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class SkillControllerTest {

	@Autowired
	AccessTokenUtil accessTokenUtil;

	@Autowired
	MockMvc mockMvc;

	static final ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private SkillService skillService;

	@Test
	@DisplayName("Should Get All skills")
	public void shouldGetAllSkills_Test() throws Exception {
		String accessToken = accessTokenUtil.obtainAccessToken("Jerymy", "password");
		Employee emp = new Employee();
		emp.setId(1L);

		Skill skill1 = new Skill();
		skill1.setId(1L);
		skill1.setDuration(10);
		skill1.setLastUsed(LocalDate.now());
		skill1.setDescription("Java");
		skill1.setEmployee(emp);

		Skill skill2 = new Skill();
		skill2.setId(2L);
		skill2.setDuration(10);
		skill2.setLastUsed(LocalDate.now());
		skill2.setDescription(".Net");
		skill2.setEmployee(emp);

		List<Skill> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);

		List<Skill> sortedAccounts = skills.stream().sorted(Comparator.comparing(Skill::getDescription))
				.collect(Collectors.toList());
		Page<Skill> skillPage = new PageImpl<>(sortedAccounts);

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "description"));
		when(skillService.getAll(1l, pageable)).thenReturn(skillPage);

		mockMvc.perform(get("/employees/1/skills?page=0&size=5&sort=description,desc").header("Authorization",
				"Bearer " + accessToken)).andExpect(status().isOk()).andExpect(jsonPath("$.content.[0].id").value("2"))
				.andExpect(jsonPath("$.content.[0].description").value(".Net"))
				.andExpect(jsonPath("$.content.[1].id").value("1"))
				.andExpect(jsonPath("$.content.[1].description").value("Java"));

		verify(skillService).getAll(1l, pageable);
	}
}
