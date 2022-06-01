package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.adapters.SkillAdapter;
import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.repositories.SkillRepository;
import com.masters.mobog.finalexercise.services.impl.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

    private SkillService service;

    @Mock
    private SkillRepository repository;
    @Mock
    private EmployeeRepository employeeRepository;
    private SkillAdapter adapter;

    @BeforeEach
    void setup(){
        adapter = new SkillAdapter();
        service = new SkillServiceImpl(repository, employeeRepository, adapter);
    }

    @Test
    @DisplayName("should add skill")
    void shouldAddSkillToEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jay");
        employee.setLastname("Rock");
        employee.setCreatedDate(LocalDateTime.now());
        employee.setLastModifiedDate(LocalDateTime.now());
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        EmployeeSkillRequest skill = new EmployeeSkillRequest();
        skill.setLastUsed(LocalDate.now());
        skill.setDescription("Skill 2");
        skill.setDuration(4);
        when(repository.save(any(Skill.class))).thenReturn(new Skill());
        // when
        Skill actual = service.addSkillToEmployee(1L, skill);
        // then
        verify(employeeRepository).findById(anyLong());
        verify(repository).save(any(Skill.class));
        assertNotNull(actual);

    }
    @Test
    @DisplayName("should update skill")
    void shouldUpdateSkillOfEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jay");
        employee.setLastname("Rock");
        employee.setCreatedDate(LocalDateTime.now());
        employee.setLastModifiedDate(LocalDateTime.now());
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setEmployee(employee);
        skill.setDescription("Skill 2");
        skill.setDuration(4);
        when(repository.findById(anyLong())).thenReturn(Optional.of(skill));
        when(repository.save(any(Skill.class))).thenReturn(skill);
        EmployeeSkillRequest request = new EmployeeSkillRequest();
        request.setLastUsed(LocalDate.now());
        request.setDescription("Sample");
        request.setDuration(10);
        // when
        Skill actual = service.updateEmployeeSkill(1L,1L, request);
        // then
        verify(employeeRepository).findById(anyLong());
        verify(repository).findById(anyLong());
        verify(repository).save(any(Skill.class));
        assertNotNull(actual);
    }
    @Test
    @DisplayName("should get all skills")
    void shouldGetAllSkillOfEmployeeWithPage(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jay");
        employee.setLastname("Rock");
        employee.setCreatedDate(LocalDateTime.now());
        employee.setLastModifiedDate(LocalDateTime.now());
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setEmployee(employee);
        skill.setDescription("Skill 2");
        skill.setDuration(4);
        Page page = mock(Page.class);
        when(page.getContent()).thenReturn(List.of(skill));
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repository.findAllByEmployeeId(anyLong(), any(Pageable.class))).thenReturn(page);
        // when
        Page<Skill> actual = service.getAllEmployeeSkills(1L, Pageable.unpaged());
        // then
        verify(employeeRepository).findById(anyLong());
        verify(repository).findAllByEmployeeId(anyLong(), any(Pageable.class));
        assertFalse(actual.getContent().isEmpty());
    }
    @Test
    @DisplayName("should delete skill of employee")
    void shouldDeleteSkillOfEmployee(){
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("Jay");
        employee.setLastname("Rock");
        employee.setCreatedDate(LocalDateTime.now());
        employee.setLastModifiedDate(LocalDateTime.now());
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setEmployee(employee);
        skill.setDescription("Skill 2");
        skill.setDuration(4);
        Page page = mock(Page.class);
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repository.findById(anyLong())).thenReturn(Optional.of(skill));
        // when
        service.deleteEmployeeSkill(1L, 1L);
        // then
        verify(employeeRepository).findById(anyLong());
        verify(repository).findById(anyLong());
        verify(repository).delete(any(Skill.class));
    }
    @Test
    @DisplayName("should throw correct exception if employee is not existing")
    void shouldThrowCorrectException_getAllEmployeeSkills(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.getAllEmployeeSkills(1L, Pageable.unpaged()));
        assertEquals(ex.getMessage(), FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if employee can not be saved")
    void shouldThrowCorrectException_create(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.addSkillToEmployee(1L, new EmployeeSkillRequest()));
        assertEquals(FinalExerciseExceptionsCode.MAPPING_EXCEPTION.getMessage(), ex.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if employee can not be saved")
    void shouldThrowCorrectException_create2(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.addSkillToEmployee(1L, new EmployeeSkillRequest()));
        assertEquals(FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION.getMessage(),ex.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if employee not found")
    void shouldThrowCorrectException_update(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.updateEmployeeSkill(1L, 1L, new EmployeeSkillRequest()));
        assertEquals(FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION.getMessage(), ex.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if employee skill is not existing")
    void shouldThrowCorrectException_update2(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.updateEmployeeSkill(1L, 1L, new EmployeeSkillRequest()));
        assertEquals(FinalExerciseExceptionsCode.SKILL_NOT_FOUND_EXCEPTION.getMessage(), ex.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if mapping exception")
    void shouldThrowCorrectException_update3(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Skill()));
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.updateEmployeeSkill(1L, 1L, new EmployeeSkillRequest()));
        assertEquals(FinalExerciseExceptionsCode.MAPPING_EXCEPTION.getMessage(), ex.getMessage());
    }
    @Test
    @DisplayName("should throw correct exception if employee can not be deleted")
    void shouldThrowCorrectException_delete(){
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //
        FinalExerciseException ex = assertThrows(FinalExerciseException.class, () -> service.deleteEmployeeSkill(1L, 1L));
        assertEquals(ex.getMessage(), FinalExerciseExceptionsCode.SKILL_NOT_FOUND_EXCEPTION.getMessage());
    }
}
