package com.masters.mobog.finalexercise.repositories;

import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.audit.AuditConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AuditConfig.class}))
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
        // given
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");

        Employee employee2 = new Employee();
        employee2.setFirstname("Dwight");
        employee2.setLastname("Schrute");

        entityManager.persist(employee1);
        entityManager.persist(employee2);
        // when
        Page<Employee> page = employeeRepository.findAll(PageRequest.of(0, 1));

        assertEquals(List.of(employee1), page.getContent());
    }

    @Test
    @DisplayName("should  find all employees without pagination")
    void shouldFindAllEmployees(){
        // given
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");

        Employee employee2 = new Employee();
        employee2.setFirstname("Dwight");
        employee2.setLastname("Schrute");

        entityManager.persist(employee1);
        entityManager.persist(employee2);
        // when
        Page<Employee> page = employeeRepository.findAll(Pageable.unpaged());

        assertEquals(List.of(employee1, employee2), page.getContent());
    }

    @Test
    @DisplayName("should save employee")
    void shouldSaveEmployee(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");
        Employee saved = employeeRepository.save(employee1);

        Employee actual = entityManager.find(Employee.class, saved.getId());

        assertEquals(actual.getId(), saved.getId());
        assertEquals(actual.getFirstname(), saved.getFirstname());
        assertEquals(actual.getLastname(), saved.getLastname());
    }
    @Test
    @DisplayName("should find employee by id")
    void shouldFindEmployeeById(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");
        Employee saved = entityManager.persist(employee1);

        Optional<Employee> actual = employeeRepository.findById(saved.getId());

        assertTrue(actual.isPresent());
        assertEquals(actual.get().getId(), saved.getId());
        assertEquals(actual.get().getFirstname(), saved.getFirstname());
        assertEquals(actual.get().getLastname(), saved.getLastname());
    }
    @Test
    @DisplayName("should save employee with audit details")
    void shouldSaveEmployeeWithAuditDetails(){
        Employee employee1 = new Employee();
        employee1.setFirstname("Jay");
        employee1.setLastname("Rock");
        Employee saved = entityManager.persist(employee1);

        assertNotNull(saved.getLastModifiedDate());
        assertNotNull(saved.getCreatedDate());
        assertNotNull(saved.getCreatedBy());
    }
}
