package com.advancejava.finalexercise.repository;

import com.advancejava.finalexercise.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSave() {
        //arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Rey");
        employee.setLastName("Lim");
        //execute
        Employee savedEmployee = employeeRepository.save(employee);

        //test
        assertThat(savedEmployee)
                .extracting("firstName", "lastName")
                .containsExactly("Rey", "Lim");
    }

}
