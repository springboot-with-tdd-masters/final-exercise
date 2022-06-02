package com.softvision.masters.tdd.employeeskilltracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.softvision.masters.tdd.employeeskilltracker.SecurityTestConfig;
import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
import com.softvision.masters.tdd.employeeskilltracker.service.EmployeeService;
import com.softvision.masters.tdd.employeeskilltracker.service.SkillService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.softvision.masters.tdd.employeeskilltracker.controller.SkillsControllerTests.SKILLS_CONTROLLER_ENDPOINT;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.EmployeeMocks.getMockEmployee1;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.UserMocks.*;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.EmployeeMocks.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = SecurityTestConfig.class)
public class EmployeesControllerTests {

    static final String EMPLOYEES_CONTROLLER_ENDPOINT = "/employees";

    @Autowired
    MockMvc mockMvc;
    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    SkillService skillService;

    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Test
    @DisplayName("Given a successful getAll, response should give HTTP status 200 with the list.")
    @WithUserDetails(MOCK_USER1_USERNAME)
    void test_getAll_success() throws Exception {
        when(employeeService.getAll(Pageable.ofSize(2)))
                .thenReturn(createMockPage(List.of(getMockEmployee1(), getMockEmployee2())));

        mockMvc.perform(get(EMPLOYEES_CONTROLLER_ENDPOINT + "?size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_1)))
                .andExpect(jsonPath("$.content[0].lastname", Matchers.is(MOCK_EMPLOYEE_LASTNAME_1)))
                .andExpect(jsonPath("$.content[1].firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_2)))
                .andExpect(jsonPath("$.content[1].lastname", Matchers.is(MOCK_EMPLOYEE_LASTNAME_2)));

        verify(employeeService, atMostOnce()).getAll(any());
    }

    @Test
    @DisplayName("Given paging and sorting request params, response should be sorted and paged correspondingly.")
    @WithUserDetails(MOCK_USER1_USERNAME)
    void test_getAll_success_withPagination() throws Exception {

        when(employeeService.getAll(pageableCaptor.capture()))
                .thenReturn(createMockPage(List.of(getMockEmployee1(), getMockEmployee2())));

        mockMvc.perform(get(EMPLOYEES_CONTROLLER_ENDPOINT)
                        .param("page", "1")
                        .param("size", "2")
                        .param("sort", "firstname,desc"))
                .andExpect(jsonPath("$.content[0].firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_1)))
                .andExpect(jsonPath("$.content[1].firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_2)))
                .andExpect(status().isOk());

        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageNumber).
                isEqualTo(1);
        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageSize)
                .isEqualTo(2);
        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getSort)
                .isEqualTo(Sort.by(
                        Sort.Order.desc("firstname")));

        verify(employeeService, atMostOnce()).getAll(any());
    }

    @Test
    @DisplayName("Given name as query param, response should only have employees name containing 'Ro'.")
    @WithUserDetails(MOCK_USER1_USERNAME)
    void test_getAll_success_withPaginationAndTitle() throws Exception {

        when(employeeService.getNameContaining(eq("Ro"), pageableCaptor.capture()))
                .thenReturn(createMockPage(List.of(getMockEmployee2())));

        mockMvc.perform(get(EMPLOYEES_CONTROLLER_ENDPOINT)
                        .param("page", "1")
                        .param("size", "2")
                        .param("name", "Ro"))
                .andExpect(jsonPath("$.content[0].firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_2)))
                .andExpect(status().isOk());

        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageNumber).
                isEqualTo(1);
        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageSize)
                .isEqualTo(2);

        verify(employeeService, atMostOnce()).getNameContaining(any(), any());
    }

    @Test
    @DisplayName("Given a record not found from service getAll, response should give http status 404 (not found).")
    @WithUserDetails(MOCK_USER1_USERNAME)
    void test_getAll_fail_notFound() throws Exception {
        when(employeeService.getAll(any())).thenThrow(RecordNotFoundException.class);

        mockMvc.perform(get(EMPLOYEES_CONTROLLER_ENDPOINT)).andExpect(status().isNotFound());

        verify(employeeService, atMostOnce()).getAll(any());
    }

    @Test
    @DisplayName("Given a successful result from create, response should give http status 201 (created).")
    @WithUserDetails(MOCK_USER1_USERNAME)
    void test_createEmployee() throws Exception {
        when(employeeService.create(argThat(employee ->
                MOCK_EMPLOYEE_FIRSTNAME_1.equals(employee.getFirstname())
                        && MOCK_EMPLOYEE_LASTNAME_1.equals(employee.getLastname()))))
                .thenReturn(getMockEmployee1());

        mockMvc.perform(post(EMPLOYEES_CONTROLLER_ENDPOINT)
                        .content(objectMapper.writeValueAsBytes(getMockEmployee1()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstname", Matchers.is(MOCK_EMPLOYEE_FIRSTNAME_1)))
                .andExpect(jsonPath("lastname", Matchers.is(MOCK_EMPLOYEE_LASTNAME_1)));

        verify(employeeService, atMostOnce()).create(any());
    }

    @Test
    @WithUserDetails(MOCK_USER1_USERNAME)
    @DisplayName("Given a successful result from create skill, response should give http status 201 (created).")
    void test_createSkill() throws Exception {
        when(employeeService.createSkill(eq(1L), argThat(
                    skill -> MOCK_SKILL_DESCRIPTION_1.equals(skill.getDescription()))))
                .thenReturn(getMockSkill1());

        mockMvc.perform(post(EMPLOYEES_CONTROLLER_ENDPOINT + "/1" + SKILLS_CONTROLLER_ENDPOINT)
                        .content(objectMapper.writeValueAsBytes(getMockSkill1()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("description", Matchers.is(MOCK_SKILL_DESCRIPTION_1)));

        verify(employeeService, atMostOnce()).createSkill(anyLong(), any());
    }

    @Test
    @DisplayName("Given an anonymous user, response should give http status 403 (forbidden).")
    @WithAnonymousUser()
    void test_unauthorized() throws Exception {
        mockMvc.perform(get(EMPLOYEES_CONTROLLER_ENDPOINT)).andExpect(status().isForbidden());
        mockMvc.perform(post(EMPLOYEES_CONTROLLER_ENDPOINT)).andExpect(status().isForbidden());
    }

    private Page<Employee> createMockPage(List<Employee> content) {
        return new PageImpl<>(content);
    }
}
