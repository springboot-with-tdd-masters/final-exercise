package com.java.finalexercise.repository;

import com.java.finalexercise.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save - should accept Employee Entity ")
    public void save() {

        //act
        Employee employee = new Employee("John", "Xina");
        Employee savedEmployee = employeeRepository.save(employee);
        //assert

        assertThat(savedEmployee)
                .extracting("firstName", "lastName")
                .containsExactly("John", "Xina");
    }

    @Test
    @DisplayName("Update - should accept Employee Entity")
    public void update() {

        Employee employee = new Employee("John", "Xina");
        Employee savedEmployee = employeeRepository.save(employee);
        //assert
        savedEmployee.setFirstName("Updated John");
        savedEmployee.setLastName("Updated Xina");

        employeeRepository.save(savedEmployee);

        assertThat(savedEmployee)
                .extracting("firstName", "lastName")
                .containsExactly("Updated John", "Updated Xina");
    }

    @Test
    @DisplayName("Find By ID - should accept ID")
    public void findById() {
        Employee employee = new Employee("John", "Xina");
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> retrievedEmployee = employeeRepository.findById(savedEmployee.getId());

        assertThat(retrievedEmployee).isNotNull();

    }

    @Test
    @DisplayName("Delete By ID - should accept ID")
    public void delete() {
        Employee employee = new Employee("John", "Xina");
        Employee savedEmployee = employeeRepository.save(employee);

        Long bookId = savedEmployee.getId();

        employeeRepository.delete(savedEmployee);

        Optional<Employee> retrievedEmployee = employeeRepository.findById(bookId);

        assertThat(retrievedEmployee).isEmpty();

    }

    @Test
    @DisplayName("Find")
    public void find() {
        Employee employee = new Employee("John", "Xina");
        employeeRepository.save(employee);

        assertThat(
                employeeRepository
                        .findAll(PageRequest.of(0, 10))
                        .getContent()
                        .size())
                .isEqualTo(1);
    }

}