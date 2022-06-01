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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    @DisplayName("should return 200 for findById")
    void shouldReturn200_getEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jane");
        employee.setLastname("Doe");
        when(employeeService.findById(1L)).thenReturn(employee);
        // when
        ResultActions actual = mvc.perform(MockMvcRequestBuilders.get("/employees/1")
        );
        // then
        verify(employeeService).findById(anyLong());
        actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.firstname", is("Jane")));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.lastname", is("Doe")));
    }
    @Test
    @DisplayName("should return 200 for update")
    void shouldReturn200_updateEmployee() throws Exception {
        EmployeeRequest request = new EmployeeRequest();
        request.setLastname("Doe");
        request.setFirstname("Jane");
        String requestString = mapper.writeValueAsString(request);

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jane");
        employee.setLastname("Doe");
        when(employeeService.update(anyLong(), any(EmployeeRequest.class))).thenReturn(employee);
        // when
        ResultActions actual = mvc.perform(MockMvcRequestBuilders.put("/employees/1")
                .content(requestString)
        );
        // then
        verify(employeeService).update(anyLong(), any(EmployeeRequest.class));
        actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.firstname", is("Jane")));
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.lastname", is("Doe")));
    }
    @Test
    @DisplayName("should return 200 for getAll with pagination")
    void shouldReturn200_GetAllWithPagination() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jane");
        employee.setLastname("Doe");
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstname("Jay");
        employee2.setLastname("Doe");
        Employee employee3 = new Employee();
        employee3.setId(3L);
        employee3.setFirstname("Jon");
        employee3.setLastname("Doe");
        when(employeeService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(employee, employee2, employee3)));
        ResultActions actual = mvc.perform(MockMvcRequestBuilders.get("/employees?page=0&size=3"));
        verify(employeeService).findAll(any(Pageable.class));
        actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        actual.andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));

    }
    @Test
    @DisplayName("should return 200 for delete employee")
    void shouldReturn200_DeleteEmployee() throws Exception {
        ResultActions actual = mvc.perform(MockMvcRequestBuilders.delete("/employees/1"));
        verify(employeeService).delete(anyLong());
        actual.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
