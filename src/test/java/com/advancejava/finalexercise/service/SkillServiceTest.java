package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.model.Skill;
import com.advancejava.finalexercise.repository.EmployeeRepository;
import com.advancejava.finalexercise.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class SkillServiceTest {


    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService service = new SkillServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // JUnit test for getAllSkills method
    @DisplayName("getAllSkills")
    @Test
    public void getAllSkills(){
        // given - precondition or setup

        Employee employee = new Employee(1L,"Rey","Lim");
        Skill newSkill = new Skill(1L,"Copy",18, LocalDate.parse("2022-06-03"),employee);
        Skill newSkill2 = new Skill(2L,"Paste",18,LocalDate.parse("2022-06-03"),employee);
        Skill newSkill3 = new Skill(3L,"Copy Paste",18,LocalDate.parse("2022-06-03"),employee);

        given(skillRepository.findAll()).willReturn(List.of(newSkill,newSkill2,newSkill3));

        List<Skill> skills = service.getAllSkills();

        assertThat(skills).isNotNull();
        assertThat(skills.size()).isEqualTo(3);
    }
}
