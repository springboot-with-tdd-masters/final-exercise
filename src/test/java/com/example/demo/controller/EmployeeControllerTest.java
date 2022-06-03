package com.example.demo.controller;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.SkillDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import com.example.demo.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({EmployeeController.class})
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    private final Long employee1Id = 1L;
    private final String employee1FN = "James";
    private final String employee1LN = "Bond";
    private final String employee2FN = "Zea";
    private final String employee2LN = "Abad";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @MockBean
    private UserDetailsService userService;

    private List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        employeeList.add(buildEmployee(1L, employee1FN, employee1LN));
        employeeList.add(buildEmployee(2L, employee2FN, employee2LN));

    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close(){
        employeeList = new ArrayList<>();
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successCreateEmployee() throws Exception {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setFirstName(employee1FN );
        employeeRequest.setLastName(employee1LN);

        when(employeeService.createNewEmployee(employeeRequest))
                .thenReturn(employeeList.get(0));

        this.mockMvc.perform(post("/employees").content(
                        objectMapper.writeValueAsString(employeeRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value(employee1FN))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value(employee1LN));

        verify(employeeService).createNewEmployee(employeeRequest);
    }

    @Test
    @DisplayName("Given a successful read by ID, response should give http status 200")
    public void shouldReadEmployeeByIdSuccess() throws Exception {
        when(employeeService.readEmployeeById(employee1Id)).thenReturn(
                employeeList.get(0));

        this.mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value(employee1FN))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value(employee1LN));

        verify(employeeService).readEmployeeById(employee1Id);
    }

    @Test
    @DisplayName("Given a successful read, response should give http status 200")
    public void successReadAllEmployeeWithSortAndPagination() throws Exception {
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("lastName").ascending());
        List<Employee> sortedEmployees = employeeList.stream().sorted(Comparator.comparing(Employee::getLastName)).collect(Collectors.toList());
        Page<Employee> employeePage = new PageImpl<>(sortedEmployees);
        when(employeeService.readAllEmployees(pageRequest)).thenReturn(employeePage);

        mockMvc.perform(get("/employees")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "lastName,asc")) // <-- no space after comma!!!
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].lastName", is(employee2LN)))
                .andExpect(jsonPath("$.content.[1].lastName", is(employee1LN)));

        verify(employeeService).readAllEmployees(pageRequest);
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successUpdateEmployee() throws Exception {
        String newLastName = "Sean";
        EmployeeRequest employeeRequest = new EmployeeRequest();
        employeeRequest.setId(employee1Id);
        employeeRequest.setFirstName(employee1FN);
        employeeRequest.setLastName(newLastName);

        when(employeeService.updateEmployee(employeeRequest))
                .thenReturn(buildUpdatedEmployee(employeeList.get(0), employeeRequest));

        this.mockMvc.perform(post("/employees").content(
                        objectMapper.writeValueAsString(employeeRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".firstName").value(employee1FN))
                .andExpect(MockMvcResultMatchers.jsonPath(".lastName").value(newLastName));

        verify(employeeService).updateEmployee(employeeRequest);
    }

    private Employee buildUpdatedEmployee(Employee employee, EmployeeRequest employeeRequest) {
        employee.setLastName(employeeRequest.getLastName());
        return employee;
    }

    @Test
    @DisplayName("Given a successful delete, response should give http status 200")
    public void successDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployeeById(1L);
        this.mockMvc
                .perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given a fail read, update is not possible and response should give http status 404")
    public void shouldReturn404EmployeeNotFoundUpdate() throws Exception {
        Long employeeId = 42L;
        when(employeeService.readEmployeeById(employeeId))
                .thenThrow(new NotFoundException(String.format("Employee with id %s not found", employeeId)));

        this.mockMvc.perform(get("/employees/42"))
                .andExpect(status().isNotFound());
    }
}