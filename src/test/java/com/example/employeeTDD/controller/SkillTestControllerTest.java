package com.example.employeeTDD.controller;

import com.example.employeeTDD.model.Skill;
import com.example.employeeTDD.model.Employee;
import com.example.employeeTDD.service.SkillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
public class SkillTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private SkillController skillController;

    @MockBean
    private EmployeeController employeeController;

    @Test
    @DisplayName("Get all skills, response should give http status 200")
    public void shouldReturnAllSkills() throws Exception {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setLastName("Zaldy");

        Skill skill = new Skill();
        skill.setDescription("JK Test");
        skill.setEmployee(employee);
        skill.setId(1L);
        skill.setDuration(10);

        when(skillController.createSkills(1L, skill)).thenReturn(skill);

        mockMvc.perform(post("/employees/{employeeId}/skills")
                        .content(mapper.writeValueAsBytes(skill))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description", Matchers.is("JK Test")))
                .andExpect(jsonPath("lastName", Matchers.is("Zaldy")));

    }

}
