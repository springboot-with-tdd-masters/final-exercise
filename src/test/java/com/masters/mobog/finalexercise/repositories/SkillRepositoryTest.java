package com.masters.mobog.finalexercise.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class SkillRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SkillRepository repository;


    @Test
    @DisplayName("should save skill")
    void shouldSaveSkill(){

    }
    @Test
    @DisplayName("should update skill")
    void shouldUpdateSkill(){

    }
    @Test
    @DisplayName("should find by skill id")
    void shouldFindBySkillId(){

    }
    @Test
    @DisplayName("should find all without pagination")
    void shouldFindAllWithoutPagination(){

    }
    @Test
    @DisplayName("should find all with pagination")
    void shouldFindAllWithPagination(){

    }
    @Test
    @DisplayName("should delete skill")
    void shouldDeleteSkill(){

    }
}
