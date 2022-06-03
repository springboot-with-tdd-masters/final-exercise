package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import com.example.demo.service.SkillServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SkillServiceImpl skillService;

    private List<Skill> skillList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        skillList.add(buildSkill(1L, "Java", 11, LocalDate.of(2022,01,01), buildEmployee(1L, "James", "Bond")));
        skillList.add(buildSkill(2L, "Angular", 5, LocalDate.of(2020,12,31), buildEmployee(1L, "James", "Bond")));

    }

    private Skill buildSkill(Long id, String description, Integer duration, LocalDate lastUsed, Employee employee) {
        return Skill.builder().id(id).description(description).duration(duration).lastUsed(lastUsed).employee(employee).build();
    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close(){
        skillList = new ArrayList<>();
    }

    @Test
    @DisplayName("Given a successful delete, response should give http status 200")
    public void successDeleteBook() throws Exception {
        doNothing().when(skillService).deleteSkillById(1L, 1L);

        this.mockMvc
                .perform(delete("/employees/1/skills/1"))
                .andExpect(status().isOk());
    }
}