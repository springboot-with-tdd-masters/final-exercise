package com.example.demo.repository;

import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@DataJpaTest
class SkillRepositoryTest {

    @MockBean
    private SkillRepository skillRepository;

    private List<Skill> skillList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        skillList.add(buildSkill(1L, "Java", 11, LocalDate.of(2022,01,01), buildEmployee(1L, "James", "Bond")));
        skillList.add(buildSkill(2L, "Angular", 5, LocalDate.of(2020,12,31), buildEmployee(1L, "James", "Bond")));

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
    @DisplayName("Should return all skills")
    public void testFindAllSkills(){
        when(skillRepository.findAll()).thenReturn(skillList);
        List<Skill> retrievedSkills = skillRepository.findAll();
        assertEquals(2, retrievedSkills.size());
    }

    @Test
    @DisplayName("Should return skill corresponding to provided employee")
    public void testFindSkillById(){
        Long requestedId = 1L;
        List<Skill> filteredSkill = skillList.stream().filter(s -> s.getEmployee().getId().equals(requestedId)).collect(Collectors.toList());
        when(skillRepository.findByEmployeeId(requestedId)).thenReturn(filteredSkill);
        List<Skill> retrievedSkill = skillRepository.findByEmployeeId(requestedId);
        assertEquals(requestedId, retrievedSkill.get(0).getEmployee().getId());
        assertEquals(2, retrievedSkill.size());
    }

    @Test
    @DisplayName("Should read skill based on employeeId and pageable")
    public void testFindSkillByPage(){
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("description").ascending());
        Long requestedId = 1L;
        List<Skill> sortedSkill = skillList.stream()
                .filter(s -> s.getEmployee().getId().equals(requestedId))
                .sorted(Comparator.comparing(Skill::getDescription))
                .collect(Collectors.toList());

        Page<Skill> skillPage = new PageImpl<>(sortedSkill);
        when(skillRepository.findByEmployeeId(requestedId, pageRequest)).thenReturn(skillPage);
        Page<Skill> requestedSkill = skillRepository.findByEmployeeId(requestedId, pageRequest);

        assertThat(requestedSkill.getContent(),hasSize(2));
        assertThat(requestedSkill.getContent().get(0).getDescription(),is("Angular"));
    }

}