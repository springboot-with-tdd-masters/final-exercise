package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
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

import java.time.LocalDateTime;
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

    @BeforeEach
    void setup(){
        service = new SkillServiceImpl(repository, employeeRepository);
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
        Skill skill = new Skill();
        skill.setId(1L);
        skill.setEmployee(employee);
        skill.setDescription("Skill 2");
        skill.setDuration(4);
        when(repository.save(any(Skill.class))).thenReturn(skill);
        // when
        Skill actual = null;
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
        when(repository.save(any(Skill.class))).thenReturn(skill);
        // when
        Skill actual = null;
        // then
        verify(employeeRepository).findById(anyLong());
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
        when(repository.findAllByEmployeeId(anyLong(), any(Pageable.class))).thenReturn(page);
        // when
        Page<Skill> actual = null;
        // then
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
        // when
        Page<Skill> actual = null;
        // then
        verify(repository).delete(any(Skill.class));
        assertFalse(actual.getContent().isEmpty());
    }
}
