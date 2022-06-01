package com.example.employeeTDD.controller;

import com.example.employeeTDD.config.OAuthHelper;
import com.example.employeeTDD.model.Skill;
import com.example.employeeTDD.model.Employee;
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

import java.time.LocalDate;

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
    private EmployeeControllerTest employeeControllerTest;

    @MockBean
    private OAuthHelper helper;

    private static final String CLIENT_ID = "devglan-client";

    private static final String NO_CLIENT_ID = "";

    private static final String USERNAME = "Zaldy";


    @Test
    @DisplayName("Get all skills, response should give http status 200")
    public void shouldReturnAllSkills() throws Exception {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Zaldy");
        employee.setLastName("Dee");

        Skill skill = new Skill();
        skill.setDescription("Java 8");
        skill.setEmployee(employee);
        skill.setId(1L);
        skill.setDuration(10);
        skill.setLastUsed(LocalDate.parse("2020-11-30"));

        when(skillController.createSkills(1L, skill)).thenReturn(skill);

        mockMvc.perform(post("/employees/{employeeId}/skills")
                        .content(mapper.writeValueAsBytes(skill))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description", Matchers.is("Java 8")))
                .andExpect(jsonPath("lastName", Matchers.is("Zaldy")));

    }

}
