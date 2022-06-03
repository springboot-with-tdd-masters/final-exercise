package com.finalexam.skills.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalexam.skills.exception.SkillNotFoundException;
import com.finalexam.skills.model.dto.request.SkillRequestDto;
import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.dto.response.SkillDto;
import com.finalexam.skills.service.EmployeeService;
import com.finalexam.skills.service.SkillService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({EmployeeController.class})
@AutoConfigureMockMvc(addFilters = false)
public class SkillControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private SkillService skillService;

  @MockBean
  private UserDetailsService userService;

  private final ObjectMapper objectMapper = new ObjectMapper();


  @Test
  @DisplayName("Should create employee and returns correct details and http status 200")
  public void shouldCreateEmployee() throws Exception {

    SkillRequestDto skillRequestDto = new SkillRequestDto("Bowling Bash", 2, "2022-06-02");
    EmployeeDto employee = new EmployeeDto();
    employee.setId(1L);
    employee.setLastname("LastName");
    employee.setFirstname("FirstName");

    SkillDto expectedResponse = new SkillDto();
    expectedResponse.setId(1L);
    expectedResponse.setDescription("Bowling Bash");
    expectedResponse.setDuration(2);
    expectedResponse.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    expectedResponse.setEmployee(employee);

    when(skillService.save(skillRequestDto, 1L))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(post("/employees/1/skills").content(
            objectMapper.writeValueAsString(skillRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bowling Bash"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstname").value("FirstName"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.lastname").value("LastName"));
    verify(skillService).save(skillRequestDto, 1L);
  }

  @Test
  @DisplayName("Should update skill name and return updated value and http status 200")
  public void shouldUpdateAndReturnNewValues() throws Exception {

    SkillRequestDto skillRequestDto = new SkillRequestDto(1, "Bowling Bash", 2, "2022-06-02");

    EmployeeDto employee = new EmployeeDto();
    employee.setId(1L);
    employee.setLastname("LastName");
    employee.setFirstname("FirstName");

    SkillDto expectedResponse = new SkillDto();
    expectedResponse.setId(1L);
    expectedResponse.setDescription("Bowling Bash");
    expectedResponse.setDuration(2);
    expectedResponse.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    expectedResponse.setEmployee(employee);

    when(skillService.update(skillRequestDto, 1L))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(post("/employees/1/skills").content(
            objectMapper.writeValueAsString(skillRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bowling Bash"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstname").value("FirstName"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.lastname").value("LastName"));

    verify(skillService).update(skillRequestDto, 1L);

  }

  @Test
  @DisplayName("Update - Should return http 404 if parameter employee or skill ID is invalid")
  public void shouldReturn404ForNonexistingEmployee_onUpdate() throws Exception {

    SkillRequestDto skillRequestDto = new SkillRequestDto(1, "Bowling Bash", 2, "2022-06-02");

    when(skillService.update(skillRequestDto, 1L))
        .thenThrow(new SkillNotFoundException());

    this.mockMvc.perform(post("/employees/1/skills").content(
            objectMapper.writeValueAsString(skillRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(skillService).update(skillRequestDto, 1L);
  }


  @Test
  @DisplayName("Should return one skill with correct details and http status 200")
  public void shouldReturnOneSkillWithCorrectDetails() throws Exception {

    EmployeeDto employee = new EmployeeDto();
    employee.setId(1L);
    employee.setLastname("LastName");
    employee.setFirstname("FirstName");

    SkillDto expectedResponse = new SkillDto();
    expectedResponse.setId(1L);
    expectedResponse.setDescription("Bowling Bash");
    expectedResponse.setDuration(2);
    expectedResponse.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    expectedResponse.setEmployee(employee);

    when(skillService.getSkillByEmployeeIdById(1L, 1L))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(get("/employees/1/skills/1"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Bowling Bash"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstname").value("FirstName"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.employee.lastname").value("LastName"));

    verify(skillService).getSkillByEmployeeIdById(1L, 1L);
  }

  @Test
  @DisplayName("Get - Should return http 404 if parameter passed ID is invalid")
  public void shouldReturn404ForNonexistingSkills_onGet() throws Exception {
    when(skillService.getSkillByEmployeeIdById(1L, 1L))
        .thenThrow(new SkillNotFoundException());

    this.mockMvc.perform(get("/employees/1/skills/1"))
        .andExpect(status().isNotFound());

    verify(skillService).getSkillByEmployeeIdById(1L, 1L);
  }

  @Test
  @DisplayName("Should delete skill and return success message with http status 200")
  public void shouldDeleteSkillAndReturn200() throws Exception {

    String expectedMessage = "Skill successfully deleted id: 2";
    doNothing().when(skillService).deleteByEmployeeIdBySkillId(1L, 2L);

    this.mockMvc.perform(delete("/employees/1/skills/2"))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedMessage));

    verify(skillService).deleteByEmployeeIdBySkillId(1L, 2L);
  }


  @Test
  @DisplayName("Should return http 404 if parameter skillID is invalid - on delete")
  public void shouldReturn404ForNonexistingSkill_onDelete() throws Exception {
    doThrow(new SkillNotFoundException()).when(skillService).deleteByEmployeeIdBySkillId(1L, 2L);
    this.mockMvc.perform(delete("/employees/1/skills/2"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Get all employees with paging and sorting")
  public void getEmployeesWithPagingAndSorting() throws Exception {

    EmployeeDto employee = new EmployeeDto();
    employee.setId(1L);
    employee.setLastname("LastName");
    employee.setFirstname("FirstName");

    SkillDto skill1 = new SkillDto();
    skill1.setId(1L);
    skill1.setDescription("Bowling Bash");
    skill1.setDuration(1);
    skill1.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    skill1.setEmployee(employee);

    SkillDto skill2 = new SkillDto();
    skill2.setId(2L);
    skill2.setDescription("Rolling thunder");
    skill2.setDuration(1);
    skill2.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    skill2.setEmployee(employee);

    SkillDto skill3 = new SkillDto();
    skill3.setId(3L);
    skill3.setDescription("Ultra Instinct");
    skill3.setDuration(3);
    skill3.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    skill3.setEmployee(employee);

    Page<SkillDto> skillDtoPage = new PageImpl(
        Arrays.asList(skill1, skill2, skill3));

    Pageable pageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "description"));

    when(skillService.getSkills(pageable))
        .thenReturn(skillDtoPage);

    this.mockMvc.perform(get("/employees/1/skills?page=0&size=20&sort=description,asc"))
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content.[0].description").value("Bowling Bash"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].duration").value(1))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content.[1].description").value("Rolling thunder"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].duration").value(1))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content.[2].description").value("Ultra Instinct"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[2].duration").value(3))
    ;
    verify(skillService).getSkills(pageable);

  }

}
