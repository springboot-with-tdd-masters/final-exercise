package com.example.employee.controllers;

import com.example.employee.controllers.advices.DefaultExceptionHandler;
import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.domain.entities.SkillEntity;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.exceptions.codes.EmployeeExceptionCode;
import com.example.employee.exceptions.codes.SkillExceptionCode;
import com.example.employee.services.SkillService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SkillController.class)
class SkillControllerTest extends AbstractControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @MockBean
    private SkillService skillService;

    private SkillController skillController;

    @BeforeEach
    void setUp() {
        skillController = new SkillController(skillService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(skillController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new DefaultExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("getAll_shouldReturnPaginatedSkills")
    void getAll_shouldReturnPaginatedSkills() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";

        final Skill skill = new Skill(description, duration, lastUsedAsString);
        skill.setEmployeeId(employeeId);

        final PageRequest pageRequest = PageRequest.of(0, 2);
        final PageImpl<Skill> pageResponse = new PageImpl<>(Arrays.asList(skill), pageRequest, 1);

        when(skillService.findAllSkillsOf(employeeId, pageRequest))
                .thenReturn(pageResponse);

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1/skills?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .findAllSkillsOf(employeeId, pageRequest);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.content[0].description", is(description)));
        resultActions.andExpect(jsonPath("$.content[0].duration", is(duration)));
        resultActions.andExpect(jsonPath("$.content[0].lastUsed", is(lastUsedAsString)));
        resultActions.andExpect(jsonPath("$.content[0].employeeId", is(1)));

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber", is(0)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize", is(2)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.totalPages", is(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements", is(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size", is(2)));
    }

    @Test
    @DisplayName("getAll_shouldReturn404WhenEmployeeDoesntExists")
    void getAll_shouldReturn404WhenEmployeeDoesntExists() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final Pageable pageRequest = PageRequest.of(0, 2);

        when(skillService.findAllSkillsOf(employeeId, pageRequest))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1/skills?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .findAllSkillsOf(employeeId, pageRequest);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("get_shouldReturnSkillWithGivenIdAndEmployeeId")
    void get_shouldReturnSkillWithGivenIdAndEmployeeId() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final Long skillId = 1L;
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";

        final Skill skill = new Skill(description, duration, lastUsedAsString);
        skill.setEmployeeId(employeeId);

        when(skillService.findSkillOf(employeeId, skillId))
                .thenReturn(skill);

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .findSkillOf(employeeId, skillId);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.description", is(description)));
        resultActions.andExpect(jsonPath("$.duration", is(duration)));
        resultActions.andExpect(jsonPath("$.lastUsed", is(lastUsedAsString)));
        resultActions.andExpect(jsonPath("$.employeeId", is(1)));
    }

    @Test
    @DisplayName("getAll_shouldReturn404WhenEmployeeDoesntExists")
    void get_shouldReturn404WhenEmployeeDoesntExists() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final Long skillId = 1L;

        when(skillService.findSkillOf(employeeId, skillId))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .findSkillOf(employeeId, skillId);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("get_shouldReturn404WhenSkillDoesntExists")
    void get_shouldReturn404WhenSkillDoesntExists() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final Long skillId = 1L;

        when(skillService.findSkillOf(employeeId, skillId))
                .thenThrow(new DefaultException(SkillExceptionCode.SKILL_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(get("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .findSkillOf(employeeId, skillId);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Skill not found."));
    }

    @Test
    @DisplayName("create_shouldCreateSkillAndReturnSkillWithProperValues")
    void create_shouldCreateSkillAndReturnSkillWithProperValues() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final String description = "Java 8";
        final Integer duration = 7;
        final String lastUsedAsString = "2022-06-03";

        final SkillRequest skillRequest = SkillRequest.of(description, duration, lastUsedAsString);
        final Skill skill = new Skill(description, duration, lastUsedAsString);
        skill.setEmployeeId(employeeId);

        when(skillService.createSkillOf(employeeId, skillRequest))
                .thenReturn(skill);

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees/1/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skillRequest)));

        // Assert
        verify(skillService)
                .createSkillOf(employeeId, skillRequest);

        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.description", is(description)));
        resultActions.andExpect(jsonPath("$.duration", is(duration)));
        resultActions.andExpect(jsonPath("$.lastUsed", is(lastUsedAsString)));
        resultActions.andExpect(jsonPath("$.employeeId", is(1)));
    }

    @Test
    @DisplayName("create_shouldReturn400WhenAnyFieldsWereInvalid")
    void create_shouldReturn400WhenAnyFieldsWereInvalid() throws Exception {
        // Arrange

        final SkillRequest skillRequest = SkillRequest.of("description", null, "lastUsedAsString");

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees/1/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skillRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("duration")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("Duration is required")));
        resultActions.andExpect(jsonPath("$.violations[1].fieldName", is("lastUsed")));
        resultActions.andExpect(jsonPath("$.violations[1].message", is("Last used skill must have a date with format yyyy-MM-dd (i.e 2020-06-03)")));
    }

    @Test
    @DisplayName("create_shouldReturn400WhenAnyFieldsWereInvalid")
    void create_shouldReturn400WhenDurationIsNegative() throws Exception {
        // Arrange

        final SkillRequest skillRequest = SkillRequest.of("description", -1, "2022-06-03");

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees/1/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skillRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("duration")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("Duration must be positive number")));
    }

    @Test
    @DisplayName("create_shouldReturn404WhenEmployeeDoesntExists")
    void create_shouldReturn404WhenEmployeeDoesntExists() throws Exception {
        // Arrange
        final Long employeeId = 1L;

        final SkillRequest skillRequest = SkillRequest.of("description", 0, "2022-06-03");

        when(skillService.createSkillOf(employeeId, skillRequest))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(post("/api/employees/1/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skillRequest)));

        // Assert
        verify(skillService)
                .createSkillOf(employeeId, skillRequest);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("update_shouldUpdateSkillWithGivenIdAndEmployeeId")
    void update_shouldUpdateSkillWithGivenIdAndEmployeeId() throws Exception {
        // Arrange
        final String changeRequestDescription = "jQuery";
        final Integer changeRequestDuration = 7;
        final String changeLastUsedRequestDateAsString = "2021-01-03";

        final Long employeeId = 1L;
        final Long existingSkillId = 1L;
        final SkillRequest changeRequest = SkillRequest.of(changeRequestDescription, changeRequestDuration, changeLastUsedRequestDateAsString);
        final Skill skill = new Skill(changeRequestDescription, changeRequestDuration, changeLastUsedRequestDateAsString);
        skill.setId(existingSkillId);
        skill.setEmployeeId(employeeId);

        when(skillService.updateSkillOf(employeeId, existingSkillId, changeRequest))
                .thenReturn(skill);

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest)));

        // Assert
        verify(skillService)
                .updateSkillOf(employeeId, existingSkillId, changeRequest);

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id", is(1)));
        resultActions.andExpect(jsonPath("$.description", is(changeRequestDescription)));
        resultActions.andExpect(jsonPath("$.duration", is(changeRequestDuration)));
        resultActions.andExpect(jsonPath("$.lastUsed", is(changeLastUsedRequestDateAsString)));
        resultActions.andExpect(jsonPath("$.employeeId", is(1)));
    }

    @Test
    @DisplayName("update_shouldReturn400WhenAnyFieldsWereInvalid")
    void update_shouldReturn400WhenAnyFieldsWereInvalid() throws Exception {
        // Arrange

        final SkillRequest changeRequest = SkillRequest.of("description", null, "lastUsedAsString");

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("duration")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("Duration is required")));
        resultActions.andExpect(jsonPath("$.violations[1].fieldName", is("lastUsed")));
        resultActions.andExpect(jsonPath("$.violations[1].message", is("Last used skill must have a date with format yyyy-MM-dd (i.e 2020-06-03)")));
    }

    @Test
    @DisplayName("update_shouldReturn400WhenDurationIsNegative")
    void update_shouldReturn400WhenDurationIsNegative() throws Exception {
        // Arrange

        final SkillRequest changeRequest = SkillRequest.of("description", -1, "2022-06-03");

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest)));

        // Assert
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.violations[0].fieldName", is("duration")));
        resultActions.andExpect(jsonPath("$.violations[0].message", is("Duration must be positive number")));
    }

    @Test
    @DisplayName("update_shouldReturn404WhenEmployeeDoesntExists")
    void update_shouldReturn404WhenEmployeeDoesntExists() throws Exception {
        // Arrange
        final Long employeeId = 1L;
        final Long existingSkillId = 1L;

        final SkillRequest changeRequest = SkillRequest.of("description", 0, "2022-06-03");

        when(skillService.updateSkillOf(employeeId, existingSkillId, changeRequest))
                .thenThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));

        // Act
        final ResultActions resultActions = mockMvc.perform(put("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(changeRequest)));

        // Assert
        verify(skillService)
                .updateSkillOf(employeeId, existingSkillId, changeRequest);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("delete_shouldDeleteSkillWithGivenIdAndEmployeeId")
    void delete_shouldDeleteSkillWithGivenIdAndEmployeeId() throws Exception {
        // Arrange
        // Act
        final ResultActions resultActions = mockMvc.perform(delete("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .deleteSkillOf(1L, 1L);

        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(content().string("Skill successfully deleted"));
    }

    @Test
    @DisplayName("delete_shouldReturn404WhenEmployeeDoesntExists")
    void delete_shouldReturn404WhenEmployeeDoesntExists() throws Exception {
        // Arrange
        doThrow(new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND))
                .when(skillService)
                .deleteSkillOf(1L, 1L);

        // Act
        final ResultActions resultActions = mockMvc.perform(delete("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .deleteSkillOf(1L, 1L);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Employee not found."));
    }

    @Test
    @DisplayName("delete_shouldReturn404WhenSkillDoesntExists")
    void delete_shouldReturn404WhenSkillDoesntExists() throws Exception {
        // Arrange
        doThrow(new DefaultException(SkillExceptionCode.SKILL_NOT_FOUND))
                .when(skillService)
                .deleteSkillOf(1L, 1L);

        // Act
        final ResultActions resultActions = mockMvc.perform(delete("/api/employees/1/skills/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        verify(skillService)
                .deleteSkillOf(1L, 1L);

        resultActions.andExpect(status().isNotFound());
        resultActions.andExpect(content().string("Skill not found."));
    }

}