package com.softvision.masters.tdd.employeeskilltracker.repository;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.*;
import static com.softvision.masters.tdd.employeeskilltracker.mocks.EmployeeMocks.*;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    EmployeeRepository employeeRepository;

    static final String[] PROPERTIES_TO_EXTRACT = {"firstname", "lastname"};

    @Test
    @DisplayName("Find By ID - should be able to retrieve an employee given the ID")
    void test_findById() {
        Employee savedEmployee = employeeRepository.save(getMockEmployee1());

        assertThat(employeeRepository.findById(savedEmployee.getId()))
                .get().extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1);
    }

    @Test
    @DisplayName("Save - should accept different employees and save the correct respective details")
    void test_save() {
        Employee savedEmployee1 = employeeRepository.save(getMockEmployee1());

        assertThat(savedEmployee1)
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1);

        Employee savedEmployee2 = employeeRepository.save(getMockEmployee2());
        assertThat(savedEmployee2)
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(MOCK_EMPLOYEE_FIRSTNAME_2, MOCK_EMPLOYEE_LASTNAME_2);
    }

    @Test
    @DisplayName("Find All (paged) - should be able to retrieve saved employees with pagination")
    void test_findAll() {
        employeeRepository.save(getMockEmployee1());
        employeeRepository.save(getMockEmployee2());

        assertThat(employeeRepository.findAll(Pageable.ofSize(2)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .containsExactly(tuple(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1),
                        tuple(MOCK_EMPLOYEE_FIRSTNAME_2, MOCK_EMPLOYEE_LASTNAME_2));

        assertThat(employeeRepository.findAll(Pageable.ofSize(1)))
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1))
                .doesNotContain(tuple(MOCK_EMPLOYEE_FIRSTNAME_2, MOCK_EMPLOYEE_LASTNAME_2));
    }



    @Test
    @DisplayName("Find By Name Containing - should be able to retrieve employees with the substring 'ia' (lowercase)")
    void test_findByNameContaining() {
        employeeRepository.save(getMockEmployee1());
        employeeRepository.save(getMockEmployee2());
        employeeRepository.save(getMockEmployee3());

        String conditionInfix = "ia";

        assertThat(employeeRepository.findByFirstnameContainingOrLastnameContaining(conditionInfix, conditionInfix,
                Pageable.unpaged()))
                .extracting(PROPERTIES_TO_EXTRACT)
                .contains(tuple(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1),
                        tuple(MOCK_EMPLOYEE_FIRSTNAME_2, MOCK_EMPLOYEE_LASTNAME_2))
                .doesNotContain(tuple(MOCK_EMPLOYEE_FIRSTNAME_3, MOCK_EMPLOYEE_LASTNAME_3));

    }

    @AfterEach
    void cleanup() {
        employeeRepository.deleteAll();
        assertThat(employeeRepository.findAll()).isEmpty();
    }
}
