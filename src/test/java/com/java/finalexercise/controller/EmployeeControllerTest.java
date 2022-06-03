package com.java.finalexercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Save endpoint should return 200 on successful service invocation.")
    public void save() throws Exception {
        //arrange
        Employee employees = new Employee("John Xina", "John Xina");
        //act
        MockHttpServletResponse response = mockMvc.perform(post("/employees")
                        .content(objectMapper.writeValueAsString(employees))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    @DisplayName("Update endpoint should return 200 on successful service invocation.")
    public void update() throws Exception {
        //arrange
        Employee employees = new Employee("John Xina", "John Xina");
        //act
        MockHttpServletResponse response = mockMvc.perform(put("/employees")
                        .content(objectMapper.writeValueAsString(employees))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    @DisplayName("Find By ID Endpoint")
    public void findById() throws Exception {

        Employee employee = new Employee("John Xina", "John Xina");

        when(employeeService.findById(1L))
                .thenReturn(employee);
        //act
        MockHttpServletResponse response = mockMvc.perform(get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(employee));

    }

    @Test
    @DisplayName("Delete endpoint should return 200 on successful service invocation.")
    public void deleteEmployee() throws Exception {

        Employee employee = new Employee("John Xina", "John Xina");

        when(employeeService.findById(1L))
                .thenReturn(employee);
        //act
        MockHttpServletResponse response = mockMvc.perform(delete("/employees/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }
}
