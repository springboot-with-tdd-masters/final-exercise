package com.example.employee.controllers;

import com.example.employee.controllers.advices.DefaultExceptionHandler;
import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.exceptions.codes.EmployeeExceptionCode;
import com.example.employee.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setup(){
        employeeController = new EmployeeController(employeeService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new DefaultExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("getAll_shouldReturnPaginatedEmployees")
    void getAll_shouldReturnPaginatedEmployees() throws Exception {
        // Arrange
        final String firstName = "James";
        final String lastName = "Rod";

        final Employee employee = new Employee(firstName, lastName);

        final PageRequest pageRequest = PageRequest.of(0, 2);
        final PageImpl<Employee> pageResponse = new PageImpl<>(Arrays.asList(employee), pageRequest, 1);

        when(employeeService.findAll(pageRequest))
                .thenReturn(pageResponse);

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(employeeService)
                .findAll(pageRequest);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.content[0].firstName", is(firstName)));
        resultActions.andExpect(jsonPath("$.content[0].lastName", is(lastName)));

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber", is(0)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize", is(2)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", is(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", is(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
    }

    @Test
    @DisplayName("get_shouldReturnEmployeeWithGivenId")
    void get_shouldReturnEmployeeWithGivenId() throws Exception {
        // Arrange
        final String firstName = "James";
        final String lastName = "Rod";

        final Employee employee = new Employee(firstName, lastName);

        when(employeeService.findById(1L))
                .thenReturn(employee);

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(employeeService)
                .findById(1L);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.firstName", is(firstName)));
        resultActions.andExpect(jsonPath("$.lastName", is(lastName)));
    }

    @Test
    @DisplayName("get_shouldReturn404WhenIdDoesntExists")
    void get_shouldReturn404WhenIdDoesntExists() throws Exception {
        // Arrange
        when(employeeService.findById(1L))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(employeeService)
                .findById(1L);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("create_shouldCreateEmployeeAndReturnEmployeeWithProperValues")
    void create_shouldCreateEmployeeAndReturnEmployeeWithProperValues() throws Exception {
        // Arrange
        final String firstName = "James";
        final String lastName = "Rod";

        final EmployeeRequest employeeRequest = EmployeeRequest.of(firstName, lastName);
        final Employee employee = new Employee(firstName, lastName);

        when(employeeService.create(employeeRequest))
                .thenReturn(employee);

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // Assert
        verify(employeeService)
                .create(employeeRequest);

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.firstName", is(firstName)));
        resultActions.andExpect(jsonPath("$.lastName", is(lastName)));
    }

    @Test
    @DisplayName("create_shouldReturn400WhenAnyFieldsWereInvalid")
    void create_shouldReturn400WhenAnyFieldsWereInvalid() throws Exception {
        // Arrange

        final EmployeeRequest employeeRequest = EmployeeRequest.of("first", "");

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("lastName")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("Last name is required")));
    }

    @Test
    @DisplayName("update_shouldUpdateEmployeeWithGivenId")
    void update_shouldUpdateEmployeeWithGivenId() throws Exception {
        // Arrange
        final Long existingEmployeeId = 1L;
        final String updatedFirstName = "Updated James";
        final String updatedLastName = "Updated Rod";

        final EmployeeRequest employeeRequest = EmployeeRequest.of(updatedFirstName, updatedLastName);
        final Employee updatedEmployee = new Employee(updatedFirstName, updatedLastName);
        updatedEmployee.setId(existingEmployeeId);

        when(employeeService.update(existingEmployeeId, employeeRequest))
                .thenReturn(updatedEmployee);

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // Assert
        verify(employeeService)
                .update(existingEmployeeId, employeeRequest);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id", is(1)));
        resultActions.andExpect(jsonPath("$.firstName", is(updatedFirstName)));
        resultActions.andExpect(jsonPath("$.lastName", is(updatedLastName)));
    }

    @Test
    @DisplayName("update_shouldThrowAnErrorWhenGivenIdDoesntExists")
    void update_shouldThrowAnErrorWhenGivenIdDoesntExists() throws Exception {
        // Arrange
        final Long existingEmployeeId = 1L;

        final EmployeeRequest employeeRequest = EmployeeRequest.of("first name", "last name");

        when(employeeService.update(existingEmployeeId, employeeRequest))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // Assert
        verify(employeeService)
                .update(existingEmployeeId, employeeRequest);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("update_shouldReturn400WhenAnyFieldsWereInvalid")
    void update_shouldReturn400WhenAnyFieldsWereInvalid() throws Exception {
        // Arrange

        final EmployeeRequest employeeRequest = EmployeeRequest.of("", "last");

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("firstName")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("First name is required")));
    }

    @Test
    @DisplayName("delete_shouldDeleteEmployeeWithGivenId")
    void delete_shouldDeleteEmployeeWithGivenId() throws Exception {
        // Arrange
        // Act
        final ResultActions resultActions = mockMvc.perform(delete("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(employeeService)
                .delete(1L);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(content().string("Employee successfully deleted"));
    }

    @Test
    @DisplayName("delete_shouldThrowAnErrorWhenIdDoesntExists")
    void delete_shouldThrowAnErrorWhenIdDoesntExists() throws Exception {
        // Arrange
        doThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND))
                .when(employeeService)
                .delete(1L);

        // Act
        final ResultActions resultActions = mockMvc.perform(delete("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(employeeService)
                .delete(1L);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }
}