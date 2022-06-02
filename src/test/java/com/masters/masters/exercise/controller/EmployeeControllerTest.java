package com.masters.masters.exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.masters.exercise.model.EmployeeDto;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.services.EmployeesServiceImpl;
import com.masters.masters.exercise.services.SkillsServiceImpl;
import com.masters.masters.exercise.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeesServiceImpl service;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private SkillsServiceImpl skillsService;

    @Test
    public void saveEmployee() throws Exception {
        EmployeeDto request = new EmployeeDto();
        request.setFirstName("name");
        request.setLastName("lastName");
        Employees response = new Employees();
        response.setFirstName("name");
        response.setLastName("lastName");
        response.setId(Long.parseLong("2"));

        when(service.createOrUpdateEmployee(Mockito.any(EmployeeDto.class))).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"));

    }
}
