package com.advancejava.finalexercise.repository;

import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SkillRepositoryTest {

    @Autowired
    private SkillRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getAll() throws Exception {
        //arrange
        Employee employee = new Employee(1L,"Rey","Lim");
        employeeRepository.save(employee);

        Skill newSkill = new Skill(1L,"Copy",18,LocalDate.parse("2022-06-03"),employee);
        Skill newSkill2 = new Skill(2L,"Paste",18,LocalDate.parse("2022-06-03"),employee);
        Skill newSkill3 = new Skill(3L,"Copy Paste",18,LocalDate.parse("2022-06-03"),employee);
        repository.save(newSkill);
        repository.save(newSkill2);
        repository.save(newSkill3);

        //execute
        List<Skill> skillList = repository.findAll();

        assertThat(skillList).isNotNull();
        assertThat(skillList.size()).isEqualTo(3);
    }

}
