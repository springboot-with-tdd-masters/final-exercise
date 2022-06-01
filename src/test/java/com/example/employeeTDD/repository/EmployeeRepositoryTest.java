package com.example.employeeTDD.repository;

import com.example.employeeTDD.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void saveEmployeeHappyPath(){
        Employee employee = new Employee();

        employee.setFirstName("Zaldy");
        employee.setLastName("Dee");
        employee.setId(1L);

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertThat(savedEmployee).extracting("firstName","lastName")
                .containsExactly("Zaldy","Dee");
    }

}
