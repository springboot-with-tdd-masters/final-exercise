package com.masters.mobog.finalexercise.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;


    @AfterEach
    void teardown(){
        entityManager.clear();
        entityManager.flush();
    }

    @Test
    @DisplayName("should  find all employees with pagination")
    void shouldFindAllEmployeesWithPagination(){

    }

    @Test
    @DisplayName("should  find all employees without pagination")
    void shouldFindAllEmployees(){

    }

    @Test
    @DisplayName("should save employee")
    void shouldSaveEmployee(){

    }
    @Test
    @DisplayName("should find employee by id")
    void shouldFindEmployeeById(){

    }
    @Test
    @DisplayName("should save employee with audit details")
    void shouldSaveEmployeeWithAuditDetails(){

    }
}
