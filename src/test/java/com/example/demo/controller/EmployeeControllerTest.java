package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeServiceImpl employeeServiceImpl;

    private List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        employeeList.add(buildEmployee(1L, "James", "Bond"));
        employeeList.add(buildEmployee(2L, "Zea", "Abad"));

    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close(){
        employeeList = new ArrayList<>();
    }
//
//    @Test
//    @DisplayName("Given a successful save, response should give http status 200")
//    public void successCreateEmployee() throws Exception {
//        EmployeeRequest employeeRequest = new EmployeeRequest();
//        employeeRequest.setFirstName("James");
//        employeeRequest.setLastName("Bond");
//
//        when(employeeServiceImpl.createNewEmployee(employeeRequest))
//                .thenReturn(employeeList.get(0));
//
//        this.mockMvc.perform(post("/employees").content(
//                        objectMapper.writeValueAsString(employeeRequest)
//                ).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Given"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Sur"));
//
//        verify(employeeServiceImpl).createNewEmployee(employeeRequest);
//    }
}