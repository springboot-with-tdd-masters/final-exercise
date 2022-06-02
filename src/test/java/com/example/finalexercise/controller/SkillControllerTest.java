package com.example.finalexercise.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.finalexercise.exception.SkillNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.Skill;
import com.example.finalexercise.model.dto.SkillDto;
import com.example.finalexercise.model.dto.SkillRequest;
import com.example.finalexercise.service.EmployeeService;
import com.example.finalexercise.service.SkillService;
import com.example.finalexercise.util.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SkillController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SkillControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SkillService skillService;
	
	@MockBean
	private EmployeeService employeeService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	@DisplayName("Should add skill to employee")
	public void shouldAddSkill() throws Exception {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    
	    when(employeeService.findEmployee(1L)).thenReturn(emp);
	    
		Skill skill = new Skill();
	    skill.setId(1L);
	    skill.setDescription("Java");
	    skill.setDuration(10);
	    skill.setLastUsed(DateUtil.parse("2022-06-02"));
	    skill.setEmployee(emp);

	    SkillDto expected = SkillDto.convertToDto(skill);

	    SkillRequest request = SkillRequest.request("Java", 10, "2022-06-02");

	    when(skillService.addSkill(request, 1L))
	    	.thenReturn(expected);
	    
	
		this.mockMvc.perform(post("/employees/1/skills").content(
            	objectMapper.writeValueAsString(request)
    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Java"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.duration").value("10"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastUsed").value("2022-06-02"));

		verify(skillService).addSkill(request, 1L);
	}
	
	@Test
	@DisplayName("Should update skill")
	public void shouldUpdateSkill() throws Exception {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    	
		Skill skill = new Skill();
	    skill.setId(1L);
	    skill.setDescription("Java 8");
	    skill.setDuration(10);
	    skill.setLastUsed(DateUtil.parse("2022-06-02"));
	    skill.setEmployee(emp);
	    
	    SkillDto expected = SkillDto.convertToDto(skill);

	    SkillRequest request = SkillRequest.request("Java 8", 10, "2022-06-02");
	    when(skillService.updateSkill(1L, 1L, request))
			.thenReturn(expected);
	    
		this.mockMvc.perform(put("/employees/1/skills/1").content(
            	objectMapper.writeValueAsString(request)
    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Java 8"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.duration").value("10"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastUsed").value("2022-06-02"));

		verify(skillService).updateSkill(1L, 1L, request);
	}
	
	@Test
	@DisplayName("Should get all skills with paging and sorting")
	public void shouldGetAllSkills() throws Exception {
		SkillDto skill = new SkillDto();
		skill.setId(1L);
		skill.setDuration(10);
	    skill.setDescription("Java");
	    
	    SkillDto skill2 = new SkillDto();
	    skill2.setId(2L);
	    skill2.setDuration(10);
	    skill2.setDescription("C#");

	    Page<SkillDto> pagedSkills = new PageImpl(Arrays.asList(skill, skill2));
	    
	 	Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "description"));
	    when(skillService.getSkills(1L, pageable)).thenReturn(pagedSkills);
	    
	    this.mockMvc.perform(get("/employees/1/skills?page=0&size=5&sort=description,desc"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].description").value("Java"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].description").value("C#"));

	    verify(skillService).getSkills(1L, pageable);
	}
	
	@Test
	@DisplayName("Should delete skill")
	public void shouldDeleteSkill() throws Exception {
		this.mockMvc.perform(delete("/employees/1/skills/1"))
			.andExpect(status().isOk());

		verify(skillService).deleteSkill(1L, 1L);
	}
	
	@Test
	@DisplayName("Should be return a 404 when no skill record found")
	public void shouldReturn404WhenNoEmployeeRecordFound() throws Exception {
		SkillRequest request = SkillRequest.request("Java 8", 10, "2022-06-02");
		   
		when(skillService.updateSkill(1L, 1L, request)).thenThrow(SkillNotFoundException.class);

		this.mockMvc.perform(put("/employees/1/skills/1").content(
            	objectMapper.writeValueAsString(request)
    		).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
}
