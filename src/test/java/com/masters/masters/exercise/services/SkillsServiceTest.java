package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import com.masters.masters.exercise.repository.EmployeesRepository;
import com.masters.masters.exercise.repository.SkillsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;


public class SkillsServiceTest {

    @Mock
    private SkillsRepository skillsRepository;

    @Mock
    private EmployeesRepository employeesRepository;

    @InjectMocks
    private SkillsServiceImpl service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllSkills() throws RecordNotFoundException {
        Employees employees = new Employees();
        employees.setLastName("lastName");
        employees.setFirstName("firstName");
        Mockito.when(employeesRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employees));
        Skills skills = new Skills();
        skills.setDuration(10);
        skills.setDescription("description");
        skills.setEmployees(new Employees());
        Pageable pageable = PageRequest.of(0,5, Sort.by("description").ascending());
        Mockito.when(skillsRepository.findByEmployees(employees,pageable)).thenReturn(new PageImpl<>(List.of(skills)));
        Page<Skills> skillsPage = service.findAllSkills(Long.parseLong("1"),pageable);
        Assertions.assertNotNull(skillsPage);
        Assertions.assertEquals(1,skillsPage.getContent().size());
    }

    @Test
    public void findAllSkillsEmployeeNotFound() throws RecordNotFoundException {
        Mockito.when(employeesRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Pageable pageable = PageRequest.of(0,5, Sort.by("description").ascending());
        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            service.findAllSkills(Long.parseLong("99"),pageable);
        });
        Assertions.assertEquals("Employee with id 99 does not exist", thrown.getMessage());;
    }
}
