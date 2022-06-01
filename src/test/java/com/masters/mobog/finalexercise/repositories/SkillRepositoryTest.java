package com.masters.mobog.finalexercise.repositories;

import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SkillRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SkillRepository repository;


    @Test
    @DisplayName("should save skill")
    void shouldSaveSkill(){
        Employee employee = new Employee();
        employee.setFirstname("Scott");
        employee.setLastname("Travis");
        Skill skill1 = new Skill();
        skill1.setDescription("Skill 1");
        skill1.setDuration(3);
        skill1.setLastUsed(LocalDate.now());
        skill1.setEmployee(employee);

        Skill saved = repository.save(skill1);

        // when
        Skill actual = entityManager.find(Skill.class, saved.getId());
        assertNotNull(actual);

    }
    @Test
    @DisplayName("should update skill")
    void shouldUpdateSkill(){
        Employee employee = new Employee();
        employee.setFirstname("Scott");
        employee.setLastname("Travis");
        Skill skill1 = new Skill();
        skill1.setDescription("Skill 1");
        skill1.setDuration(3);
        skill1.setLastUsed(LocalDate.now());
        skill1.setEmployee(employee);

        Skill saved = repository.save(skill1);

        // when
        Skill update = new Skill();
        update.setId(saved.getId());
        update.setDescription("Skill 2");
        update.setDuration(update.getDuration());
        update.setLastUsed(saved.getLastUsed());

        Skill actual = repository.save(update);
        assertEquals(actual.getId(), update.getId());
        assertEquals(actual.getDescription(), "Skill 2");
    }
    @Test
    @DisplayName("should find by skill id")
    void shouldFindBySkillId(){
        Employee employee = new Employee();
        employee.setFirstname("Scott");
        employee.setLastname("Travis");
        Skill skill1 = new Skill();
        skill1.setDescription("Skill 1");
        skill1.setDuration(3);
        skill1.setLastUsed(LocalDate.now());
        skill1.setEmployee(employee);

        Skill saved = entityManager.persist(skill1);

        // when
        Optional<Skill> actual = repository.findById(saved.getId());
        assertTrue(actual.isPresent());
    }
    @Test
    @DisplayName("should find all without pagination")
    void shouldFindAllWithoutPagination(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");


        entityManager.persist(employee1);

        Skill skill = new Skill();
        skill.setDuration(2);
        skill.setDescription("Skill 1");
        skill.setLastUsed(LocalDate.now());
        skill.setEmployee(employee1);

        Skill skill2 = new Skill();
        skill2.setDuration(2);
        skill2.setDescription("Skill 2");
        skill2.setLastUsed(LocalDate.now());
        skill2.setEmployee(employee1);
        entityManager.persist(skill);
        entityManager.persist(skill2);
        // when
        Page<Skill> page = repository.findAll(Pageable.unpaged());

        assertEquals(List.of(skill, skill2), page.getContent());
    }
    @Test
    @DisplayName("should find all with pagination")
    void shouldFindAllWithPagination(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");


        entityManager.persist(employee1);

        Skill skill = new Skill();
        skill.setDuration(2);
        skill.setDescription("Skill 1");
        skill.setLastUsed(LocalDate.now());
        skill.setEmployee(employee1);

        Skill skill2 = new Skill();
        skill2.setDuration(2);
        skill2.setDescription("Skill 2");
        skill2.setLastUsed(LocalDate.now());
        skill2.setEmployee(employee1);
        entityManager.persist(skill);
        entityManager.persist(skill2);
        // when
        Page<Skill> page = repository.findAll(PageRequest.of(0, 1));

        assertEquals(List.of(skill), page.getContent());
    }
    @Test
    @DisplayName("should delete skill")
    void shouldDeleteSkill(){
        Employee employee = new Employee();
        employee.setFirstname("Scott");
        employee.setLastname("Travis");
        Skill skill1 = new Skill();
        skill1.setDescription("Skill 1");
        skill1.setDuration(3);
        skill1.setLastUsed(LocalDate.now());
        skill1.setEmployee(employee);

        Skill saved = entityManager.persist(skill1);

        // when
        repository.delete(saved);
        // then
        Optional<Skill> actual = repository.findById(saved.getId());
        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("should get skills by employee id")
    void shouldGetSkillsByEmployeeId(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");
        Employee employee2 = new Employee();
        employee2.setFirstname("Jay");
        employee2.setLastname("Rock");


        entityManager.persist(employee1);

        Skill skill = new Skill();
        skill.setDuration(2);
        skill.setDescription("Skill 1");
        skill.setLastUsed(LocalDate.now());
        skill.setEmployee(employee1);

        Skill skill2 = new Skill();
        skill2.setDuration(2);
        skill2.setDescription("Skill 2");
        skill2.setLastUsed(LocalDate.now());
        skill2.setEmployee(employee1);

        Skill skill3 = new Skill();
        skill3.setDuration(2);
        skill3.setDescription("Skill 2");
        skill3.setLastUsed(LocalDate.now());
        skill3.setEmployee(employee2);
        entityManager.persist(skill);
        entityManager.persist(skill2);
        entityManager.persist(skill3);

        Page<Skill> actual = repository.findAllByEmployeeId(PageRequest.of(0, 2));

        assertEquals(List.of(skill, skill2), actual.getContent());
    }
}
