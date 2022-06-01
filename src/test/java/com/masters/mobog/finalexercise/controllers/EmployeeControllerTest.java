package com.masters.mobog.finalexercise.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.exceptions.GlobalExceptionHandler;
import com.masters.mobog.finalexercise.services.EmployeeService;
import com.masters.mobog.finalexercise.services.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {
    private MockMvc mvc;

    private EmployeeController controller;

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private SkillService skillService;

    @BeforeEach
    void setup() {
        controller = new EmployeeController(employeeService, skillService);
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }
    @Test
    @DisplayName("should return 200 for create employee")
    void shouldReturn200_createEmployee() throws Exception {
        // given
        EmployeeRequest request = new EmployeeRequest();
        request.setLastname("Doe");
        request.setFirstname("Jane");
        String requestString = mapper.writeValueAsString(request);
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jane");
        employee.setLastname("Doe");
        when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(employee);
        // when
        ResultActions actual = mvc.perform(MockMvcRequestBuilders.post("/employees")
                .content(requestString)
                .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        verify(employeeService).createEmployee(any(EmployeeRequest.class));
        actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.firstname", is("Jane")));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.lastname", is("Doe")));


    }
    @Test
    @DisplayName("should return 200")
    void shouldReturn200_getEmployeeById(){

    }
    @Test
    @DisplayName("should return 200")
    void should(){

    }
}
