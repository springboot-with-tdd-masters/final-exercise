package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@DataJpaTest
public class SkillsRepositoryTest {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private  SkillsRepository skillsRepository;

    @Test
    public void getAllSkills(){
        Employees employees = new Employees();
        employees.setFirstName("firstName");
        employees.setLastName("lastName");
        Employees savedEmployee = employeesRepository.save(employees);
        Skills skills = new Skills();
        skills.setLastUsed(LocalDate.now());
        skills.setDuration(10);
        skills.setDescription("description");
        skills.setEmployees(savedEmployee);
        skillsRepository.save(skills);
        Pageable pageable = PageRequest.of(0,5, Sort.by("description").ascending());
        Page<Skills> skillsPage = skillsRepository.findByEmployees(savedEmployee,pageable);
        Assertions.assertNotNull(skillsPage);
        Assertions.assertEquals(1,skillsPage.getContent().size());
    }

}
