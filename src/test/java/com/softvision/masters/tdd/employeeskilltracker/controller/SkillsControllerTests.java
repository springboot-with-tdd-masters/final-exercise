package com.softvision.masters.tdd.employeeskilltracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softvision.masters.tdd.employeeskilltracker.SecurityTestConfig;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.softvision.masters.tdd.employeeskilltracker.mocks.UserMocks.*;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.EmployeeMocks.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = SecurityTestConfig.class)
public class SkillsControllerTests {

    static final String SKILLS_CONTROLLER_ENDPOINT = "/skills";

    @Autowired
    MockMvc mockMvc;
    @Captor
    ArgumentCaptor<Pageable> pageableCaptor;

    @MockBean
    SkillService skillService;

    static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithUserDetails(MOCK_USER2_USERNAME)
    @DisplayName("Given a successful getAll, response should give http status 200 with the list.")
    void test_getAll_success() throws Exception {
        when(skillService.getAll(Pageable.ofSize(2)))
                .thenReturn(createMockPage(List.of(getMockSkill1(), getMockSkill2())));

        mockMvc.perform(get(SKILLS_CONTROLLER_ENDPOINT)
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].description", Matchers.is(MOCK_SKILL_DESCRIPTION_1)))
                .andExpect(jsonPath("$.content[1].description", Matchers.is(MOCK_SKILL_DESCRIPTION_2)));

        verify(skillService, atMostOnce()).getAll(any());
    }

    @Test
    @WithUserDetails(MOCK_USER2_USERNAME)
    @DisplayName("Given paging and sorting request params, response should be sorted and paged correspondingly.")
    void test_getAll_success_withPagination() throws Exception {

        when(skillService.getAll(pageableCaptor.capture()))
                .thenReturn(createMockPage(List.of(getMockSkill1(), getMockSkill2())));

        mockMvc.perform(get(SKILLS_CONTROLLER_ENDPOINT)
                    .param("page", "1")
                    .param("size", "2")
                    .param("sort", "id,asc")
                    .param("sort", "description,desc"))
                .andExpect(jsonPath("$.content[0].description", Matchers.is(MOCK_SKILL_DESCRIPTION_1)))
                .andExpect(jsonPath("$.content[1].description", Matchers.is(MOCK_SKILL_DESCRIPTION_2)))
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
                        Sort.Order.asc("id"),
                        Sort.Order.desc("description")));

        verify(skillService, atMostOnce()).getAll(any());
    }

    @Test
    @WithUserDetails(MOCK_USER2_USERNAME)
    @DisplayName("Given title as query param, response should only have skill description containing 'Mil'.")
    void test_getAll_success_withPaginationAndTitle() throws Exception {

        when(skillService.getDescriptionContaining(eq("Mil"), pageableCaptor.capture()))
                .thenReturn(createMockPage(List.of(getMockSkill1())));

        mockMvc.perform(get(SKILLS_CONTROLLER_ENDPOINT)
                        .param("page", "1")
                        .param("size", "2")
                        .param("description", "Mil"))
                .andExpect(jsonPath("$.content[0].description", Matchers.is(MOCK_SKILL_DESCRIPTION_1)))
                .andExpect(status().isOk());

        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageNumber).
                isEqualTo(1);
        Assertions.assertThat(pageable)
                .extracting(AbstractPageRequest::getPageSize)
                .isEqualTo(2);

        verify(skillService, atMostOnce()).getDescriptionContaining(any(), any());
    }

    @Test
    @WithUserDetails(MOCK_USER2_USERNAME)
    @DisplayName("Given record was not found from service getAll, response should give http status 404 (not found).")
    void test_getAll_fail() throws Exception {
        when(skillService.getAll(any())).thenThrow(RecordNotFoundException.class);

        mockMvc.perform(get(SKILLS_CONTROLLER_ENDPOINT)).andExpect(status().isNotFound());

        verify(skillService, atMostOnce()).getAll(any());
    }

    @Test
    @DisplayName("Given an anonymous user, response should give http status 403 (forbidden).")
    @WithAnonymousUser()
    void test_getAll_fail_unauthorized() throws Exception {
        mockMvc.perform(get(SKILLS_CONTROLLER_ENDPOINT)).andExpect(status().isForbidden());
    }

    private Page<Skill> createMockPage(List<Skill> content) {
        return new PageImpl<>(content);
    }
}
