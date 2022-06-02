package com.softvision.masters.tdd.employeeskilltracker.repository;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.EmployeeMocks.*;

@DataJpaTest
public class SkillRepositoryTests {

    @Autowired
    SkillRepository skillRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    static final String[] PROPERTIES_TO_EXTRACT = {"description", "duration"};

    @Test
    @DisplayName("Find By ID - should be able to retrieve a skill given the ID")
    void test_findById() {
        Employee savedEmployee1 = employeeRepository.save(getMockEmployee1());
        Skill skill1 = getMockSkill1();
        skill1.setEmployee(savedEmployee1);
        skillRepository.save(skill1);

        assertThat(skillRepository.findById(skill1.getId()))
                .get().extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1);
    }

    @Test
    @DisplayName("Save - should accept different skills and save the correct respective details")
    void test_save() {
        Employee employee1 = getMockEmployee1();
        employee1 = employeeRepository.save(employee1);
        Skill skill1 = getMockSkill1();
        skill1.setEmployee(employee1);

        assertThat(skillRepository.save(skill1))
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1);

        Employee employee2 = getMockEmployee2();
        employee2 = employeeRepository.save(employee2);
        Skill skill2 = getMockSkill2();
        skill2.setEmployee(employee2);

        assertThat(skillRepository.save(skill2))
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2);
    }

    @Test
    @DisplayName("Find All (paged) - should be able to retrieve all saved skills with pagination")
    void test_findAll() {
        Employee savedEmployee1 = employeeRepository.save(getMockEmployee1());
        Skill savedSkill1 = getMockSkill1();
        savedSkill1.setEmployee(savedEmployee1);
        skillRepository.save(savedSkill1);

        Employee savedEmployee2 = employeeRepository.save(getMockEmployee2());
        Skill savedSKill2 = getMockSkill2();
        savedSKill2.setEmployee(savedEmployee2);
        skillRepository.save(savedSKill2);

        assertThat(skillRepository.findAll(Pageable.ofSize(2)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(tuple(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1),
                        tuple(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2));

        assertThat(skillRepository.findAll(Pageable.ofSize(1)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1))
                .doesNotContain(tuple(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2));

        String conditionInfix = "ng";
        assertThat(skillRepository.findByDescriptionContaining(conditionInfix, Pageable.ofSize(2)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2))
                .doesNotContain(tuple(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1));
    }

    @Test
    @DisplayName("Find Description Containing (paged) - should be able to retrieve all saved skills with 'ng' (lowercase) and pagination")
    void test_findDescriptionContaining() {
        Employee savedEmployee1 = employeeRepository.save(getMockEmployee1());
        Skill savedSkill1 = getMockSkill1();
        savedSkill1.setEmployee(savedEmployee1);
        skillRepository.save(savedSkill1);

        Employee savedEmployee2 = employeeRepository.save(getMockEmployee2());
        Skill savedSKill2 = getMockSkill2();
        savedSKill2.setEmployee(savedEmployee2);
        skillRepository.save(savedSKill2);

        String conditionInfix = "ng";
        assertThat(skillRepository.findByDescriptionContaining(conditionInfix, Pageable.ofSize(2)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2))
                .doesNotContain(tuple(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1));
    }

    @AfterEach
    void cleanup() {
        skillRepository.deleteAll();
        assertThat(skillRepository.findAll()).isEmpty();
    }
}
