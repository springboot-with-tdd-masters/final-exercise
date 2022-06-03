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
import com.finalexam.skills.exception.EmployeeNotFoundException;
import com.finalexam.skills.model.dto.request.EmployeeRequestDto;
import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.service.EmployeeService;
import com.finalexam.skills.service.SkillService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest({EmployeeController.class})
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @MockBean
  private SkillService skillService;

  private ObjectMapper objectMapper = new ObjectMapper();


  @Test
  @DisplayName("Should create employee and returns correct details and http status 200")
  public void shouldCreateEmployee() throws Exception {
    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto("Given", "Sur");

    EmployeeDto expectedResponse = new EmployeeDto();
    expectedResponse.setFirstname("Given");
    expectedResponse.setLastname("Sur");

    when(employeeService.save(employeeRequestDto))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(post("/employees").content(
            objectMapper.writeValueAsString(employeeRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Given"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Sur"));

    verify(employeeService).save(employeeRequestDto);
  }

  @Test
  @DisplayName("Should update employee name and return updated value and http status 200")
  public void shouldUpdateAndReturnNewValues() throws Exception {
    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(1L,"Given", "Sur");

    EmployeeDto expectedResponse = new EmployeeDto();
    expectedResponse.setId(1L);
    expectedResponse.setFirstname("Given");
    expectedResponse.setLastname("Sur");

    when(employeeService.update(employeeRequestDto))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(post("/employees").content(
            objectMapper.writeValueAsString(employeeRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Given"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Sur"));

    verify(employeeService).update(employeeRequestDto);

  }

  @Test
  @DisplayName("Update - Should return http 404 if parameter passed ID is invalid")
  public void shouldReturn404ForNonexistingEmployee_onUpdate() throws Exception {
    EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(1L,"Given", "Sur");

    when(employeeService.update(employeeRequestDto))
        .thenThrow(new EmployeeNotFoundException());

    this.mockMvc.perform(post("/employees").content(
            objectMapper.writeValueAsString(employeeRequestDto)
        ).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(employeeService).update(employeeRequestDto);
  }

  @Test
  @DisplayName("Should return one account with correct details and http status 200")
  public void shouldReturnOneEmployeeWithCorrectDetails() throws Exception {
    EmployeeDto expectedResponse = new EmployeeDto();
    expectedResponse.setId(1L);
    expectedResponse.setFirstname("Given");
    expectedResponse.setLastname("Sur");

    when(employeeService.getEmployeeById(1L))
        .thenReturn(expectedResponse);

    this.mockMvc.perform(get("/employees/1"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value("Given"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("Sur"));

    verify(employeeService).getEmployeeById(1L);
  }

  @Test
  @DisplayName("Get - Should return http 404 if parameter passed ID is invalid")
  public void shouldReturn404ForNonexistingEmployee_onGet() throws Exception {
    when(employeeService.getEmployeeById(1L))
        .thenThrow(new EmployeeNotFoundException());

    this.mockMvc.perform(get("/employees/1"))
        .andExpect(status().isNotFound());

    verify(employeeService).getEmployeeById(1L);
  }

  @Test
  @DisplayName("Should delete employee and return success message with http status 200")
  public void shouldDeleteEmployeeAndReturn200() throws Exception {

    String expectedMessage = "Employee successfully deleted id: 1";
    doNothing().when(employeeService).deleteEmployeeById(1L);

    this.mockMvc.perform(delete("/employees/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedMessage));

    verify(employeeService).deleteEmployeeById(1L);
  }


  @Test
  @DisplayName("Should return http 404 if parameter passed ID is invalid - on delete")
  public void shouldReturn404ForNonexistingEmployee_onDelete() throws Exception {
    doThrow(new EmployeeNotFoundException()).when(employeeService).deleteEmployeeById(1L);
    this.mockMvc.perform(delete("/employees/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Get all employees with paging and sorting")
  public void getEmployeesWithPagingAndSorting() throws Exception {
    EmployeeDto employee1 = new EmployeeDto();
    employee1.setId(1L);
    employee1.setFirstname("AAA1");
    employee1.setLastname("BBB1");
    EmployeeDto employee2 = new EmployeeDto();
    employee2.setId(2L);
    employee2.setFirstname("AAA2");
    employee2.setLastname("BBB2");
    EmployeeDto employee3 = new EmployeeDto();
    employee3.setId(3L);
    employee3.setFirstname("AAA3");
    employee3.setLastname("BBB3");

    Page<EmployeeDto>  employeeDtoPage = new PageImpl(
        Arrays.asList(employee1, employee2, employee3));

    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "firstname"));

    when(employeeService.getEmployees(pageable))
        .thenReturn(employeeDtoPage);

    this.mockMvc.perform(get("/employees?page=0&size=20&sort=firstname,desc"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].firstname").value("AAA1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].lastname").value("BBB1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].id").value("2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].firstname").value("AAA2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[1].lastname").value("BBB2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[2].id").value("3"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[2].firstname").value("AAA3"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.[2].lastname").value("BBB3"))

    ;
    verify(employeeService).getEmployees(pageable);

  }
}

