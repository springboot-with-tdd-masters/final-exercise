package com.masters.finalexercise.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.finalexercise.config.JwtAuthenticationEntryPoint;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;
import com.masters.finalexercise.service.EmployeeService;
import com.masters.finalexercise.service.JwtUserDetailsService;
import com.masters.finalexercise.service.SkillService;
import com.masters.finalexercise.util.JwtTokenUtil;

@WebMvcTest(value = EmployeeController.class, includeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenUtil.class)})
public class EmployeeControllerTest {

	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUserDetailsService myUserDetailsService;
	
    @Autowired
    private JwtTokenUtil jwtUtil;
    
    @MockBean
    private EmployeeService employeeService;
    
    @MockBean
    private SkillService skillService;
    
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private ObjectMapper mapper;
	
    @Test
    void testCreateEmployee() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employeeRequest = new Employee();
    	employeeRequest.setFirstname("dummyfname");
    	employeeRequest.setLastname("dummylname");
    	
    	Employee employeeResponse = new Employee();
    	employeeResponse.setId(1L);
    	employeeResponse.setFirstname("dummyfname");
    	employeeResponse.setLastname("dummylname");
    	
    	String jsonCreate = mapper.writeValueAsString(employeeRequest);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .post("/employees")
                .content(jsonCreate)
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	when(employeeService.save(employeeRequest)).thenReturn(employeeResponse);
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn();
    	
    	verify(employeeService).save(employeeRequest);
    	
    }
    
    @Test
    void testFindAllEmployee() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	List<Employee> employees = new ArrayList<Employee>();
    	Employee employee = new Employee();
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	employees.add(employee);
    	
    	final Page<Employee> employeesPage = new PageImpl<>(employees);
    	
    	when(employeeService.findAll(0, 10, "id")).thenReturn(employeesPage);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .get("/employees")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    	
    	verify(employeeService).findAll(0, 10, "id");
    	
    }
    
    @Test
    void testFindEmployeeById() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	when(employeeService.findById(1L)).thenReturn(employee);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/1")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    	
    	verify(employeeService).findById(1L);
    	
    }
    
    @Test
    void testDeleteEmployeeById() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	when(employeeService.findById(1L)).thenReturn(employee);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .delete("/employees/1")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    	
    	verify(employeeService).delete(1L);
    	
    }
    
    @Test
    void testEmployeeCreateSkill() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Skill skill = new Skill();
    	skill.setDescription("dummyDesc");
    	skill.setLastUsed(LocalDate.now());
    	skill.setDuration(7);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	when(skillService.save(skill, 1L)).thenReturn(skill);
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .post("/employees/1/skills")
                .content(mapper.writeValueAsString(skill))
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    	verify(skillService).save(skill, 1L);
    	
    }
    
    @Test
    void testEmployeeFindAllSkills() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	List<Skill> skills = new ArrayList<Skill>();
    	Skill skill = new Skill();
    	skill.setId(1L);
    	skill.setDescription("dummyDesc");
    	skill.setDuration(7);
    	skill.setLastUsed(LocalDate.now());
    	skill.setEmployee(employee);
    	skills.add(skill);
    	
    	final Page<Skill> skillsPage = new PageImpl<>(skills);
    	
    	when(skillService.findAll(1L, 0, 10, "id")).thenReturn(skillsPage);
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/1/skills")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    	verify(skillService).findAll(1L, 0, 10, "id");
    	
    }
    
    @Test
    void testEmployeeFindSkillById() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	Skill skill = new Skill();
    	skill.setId(1L);
    	skill.setDescription("dummyDesc");
    	skill.setDuration(7);
    	skill.setLastUsed(LocalDate.now());
    	skill.setEmployee(employee);
    	
    	when(skillService.findById(1L, 1L)).thenReturn(skill);
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .get("/employees/1/skills/1")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    	verify(skillService).findById(1L, 1L);
    	
    }
    
    @Test
    void testEmployeeDeleteSkillById() throws Exception {
    	
    	UserDetails dummy = new User("user1", "password", new ArrayList<>());
    	String jwtToken = jwtUtil.generateToken(dummy);
    	
    	Employee employee = new Employee();
    	employee.setId(1L);
    	employee.setFirstname("dummyfname");
    	employee.setLastname("dummylname");
    	
    	Skill skill = new Skill();
    	skill.setId(1L);
    	skill.setDescription("dummyDesc");
    	skill.setDuration(7);
    	skill.setLastUsed(LocalDate.now());
    	skill.setEmployee(employee);
    	
    	when(skillService.findById(1L, 1L)).thenReturn(skill);
    	when(myUserDetailsService.loadUserByUsername("user1")).thenReturn(dummy);
    	
    	RequestBuilder request = MockMvcRequestBuilders
                .delete("/employees/skills/1")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON);
    	
    	mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    	verify(skillService).delete(1L);
    	
    }
    
}
