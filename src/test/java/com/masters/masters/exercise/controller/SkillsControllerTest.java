package com.masters.masters.exercise.controller;

import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import com.masters.masters.exercise.services.EmployeesServiceImpl;
import com.masters.masters.exercise.services.SkillsServiceImpl;
import com.masters.masters.exercise.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SkillsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeesServiceImpl service;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SkillsServiceImpl skillsService;

    @Test
    public void findAllSkills() throws Exception {
        Skills skill1 = new Skills();
        skill1.setEmployees(new Employees());
        skill1.setDescription("description");
        skill1.setDuration(10);
        skill1.setLastUsed(LocalDate.now());
        Skills skill2 = new Skills();
        skill2.setEmployees(new Employees());
        skill2.setDescription("description2");
        skill2.setDuration(20);
        skill2.setLastUsed(LocalDate.now());
        Pageable pageable = PageRequest.of(0,20);
        Mockito.when(skillsService.findAllSkills(Long.parseLong("1"),pageable)).thenReturn(new PageImpl<>(List.of(skill1,skill2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].duration").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].description").value("description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].duration").value("20"));
    }


}

