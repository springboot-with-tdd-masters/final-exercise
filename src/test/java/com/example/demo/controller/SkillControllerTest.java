package com.example.demo.controller;

import com.example.demo.dto.SkillDto;
import com.example.demo.dto.SkillRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import com.example.demo.service.SkillServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SkillController.class})
@AutoConfigureMockMvc(addFilters = false)
class SkillControllerTest {

    private final String employee1FN = "James";
    private final String employee1LN = "Bond";
    private final String skill1Description = "Java";
    private final Integer skill1Duration = 11;
    private final LocalDate skill1LastUsed = LocalDate.of(2022, 01, 01);
    private final String skill2Description = "Angular";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SkillServiceImpl skillService;

    @MockBean
    private UserDetailsService userService;

    private List<Skill> skillList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        skillList.add(buildSkill(1L, skill1Description, 11, LocalDate.of(2022,01,01), buildEmployee(1L, "James", "Bond")));
        skillList.add(buildSkill(2L, skill2Description, 5, LocalDate.of(2020,12,31), buildEmployee(1L, "James", "Bond")));

    }

    private Skill buildSkill(Long id, String description, Integer duration, LocalDate lastUsed, Employee employee) {
        return Skill.builder().id(id).description(description).duration(duration).lastUsed(lastUsed).employee(employee).build();
    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @AfterEach
    public void close(){
        skillList = new ArrayList<>();
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successCreateSkill() throws Exception {
        Long employeeId = 1L;
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setDescription(skill1Description);
        skillRequest.setDuration(skill1Duration);
        skillRequest.setLastUsed(skill1LastUsed.toString());

        when(skillService.createNewSkill(employeeId, skillRequest))
                .thenReturn(SkillDto.convertToDto(skillList.get(0)));

        this.mockMvc.perform(post("/employees/1/skills").content(
                        objectMapper.writeValueAsString(skillRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".description").value(skill1Description))
                .andExpect(MockMvcResultMatchers.jsonPath(".duration").value(skill1Duration));

        verify(skillService).createNewSkill(employeeId, skillRequest);
    }

    @Test
    @DisplayName("Given a successful read, response should give http status 200")
    public void successReadAllBooksWithSortAndPagination() throws Exception {
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("description").ascending());

        List<SkillDto> sortedSkillsDto = new ArrayList<>();
        List<Skill> sortedSkills = skillList.stream().sorted(Comparator.comparing(Skill::getDescription)).collect(Collectors.toList());
        sortedSkillsDto.add(SkillDto.convertToDto(sortedSkills.get(0)));
        sortedSkillsDto.add(SkillDto.convertToDto(sortedSkills.get(1)));
        Page<SkillDto> skillPage = new PageImpl<>(sortedSkillsDto);
        when(skillService.readAllSkills(1L, pageRequest)).thenReturn(skillPage);

        mockMvc.perform(get("/employees/1/skills")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "description,asc")) // <-- no space after comma!!!
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].description", is(skill2Description)))
                .andExpect(jsonPath("$.content.[1].description", is(skill1Description)));

        verify(skillService).readAllSkills(1L, pageRequest);
    }

    @Test
    @DisplayName("Given a successful read by update, response should give http status 200")
    public void shouldUpdateSkillSuccess() throws Exception {
        Long employeeId = 1L;
        Long skillId = 1L;
        Integer newSkillDuration = 12;
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setDescription(skill1Description);
        skillRequest.setDuration(newSkillDuration);
        skillRequest.setLastUsed(skill1LastUsed.toString());

        when(skillService.updateSkill(employeeId, skillId, skillRequest))
                .thenReturn(SkillDto.convertToDto(buildNewSkill(skillList.get(0), skillRequest)));

        this.mockMvc.perform(put("/employees/1/skills/1").content(
                        objectMapper.writeValueAsString(skillRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".description").value(skill1Description))
                .andExpect(MockMvcResultMatchers.jsonPath(".duration").value(newSkillDuration));

        verify(skillService).updateSkill(employeeId, skillId, skillRequest);
    }

    private Skill buildNewSkill(Skill skill, SkillRequest skillRequest) {
        skill.setDuration(skillRequest.getDuration());
        return skill;
    }

    @Test
    @DisplayName("Given a successful delete, response should give http status 200")
    public void successDeleteBook() throws Exception {
        doNothing().when(skillService).deleteSkillById(1L, 1L);

        this.mockMvc
                .perform(delete("/employees/1/skills/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given a fail read, response should give http status 404")
    public void shouldReturn404SkillNotFoundGet() throws Exception {
        Long employeeId = 1L;
        Long skillId = 1L;
        Integer newSkillDuration = 12;
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setDescription(skill1Description);
        skillRequest.setDuration(newSkillDuration);
        skillRequest.setLastUsed(skill1LastUsed.toString());

        when(skillService.updateSkill(employeeId, skillId, skillRequest))
                .thenThrow(new NotFoundException(String.format("Skill ID %s of the given Employee is not found", employeeId)));
        this.mockMvc.perform(put("/employees/1/skills/1").content(
                        objectMapper.writeValueAsString(skillRequest)
                ).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}