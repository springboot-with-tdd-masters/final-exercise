package com.finalexam.skills.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.finalexam.skills.model.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  EmployeeRepository employeeRepository;

  @Test
  @DisplayName("Should save employee with the correct details")
  public void saveRegularAccount() {

    Employee newEmployee = new Employee("John", "Wick");

    Employee savedEmployee = employeeRepository.save(newEmployee);

    assertThat(savedEmployee.getFirstname()).isEqualTo("John");
    assertThat(savedEmployee.getLastname()).isEqualTo("Wick");
    assertThat(savedEmployee.getCreatedAt()).isNotNull();
    assertThat(savedEmployee.getUpdatedAt()).isNotNull();
    assertThat(savedEmployee.getId()).isNotNull();

  }

  @Test
  @DisplayName("Should return page object containing 3 employees")
  public void getPageObjectWith3EmployeeContent() {

    Employee employee1 = new Employee("John", "Wick");
    Employee employee2 = new Employee("Stephen", "Strange");
    Employee employee3 = new Employee("Tony", "Stark");

    employeeRepository.save(employee1);
    employeeRepository.save(employee2);
    employeeRepository.save(employee3);

    Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.ASC, "lastname"));
    Page<Employee> pagedEmployees = employeeRepository.findAll(pageable);

    assertThat(1).isEqualTo(pagedEmployees.getTotalPages());
    assertThat(3).isEqualTo(pagedEmployees.getTotalElements());
    assertThat(3).isEqualTo(pagedEmployees.getNumberOfElements());
    assertThat(3).isEqualTo(pagedEmployees.getContent().size());
    assertThat("Stark").isEqualTo(pagedEmployees.getContent().get(0).getLastname());
    assertThat("Strange").isEqualTo(pagedEmployees.getContent().get(1).getLastname());
    assertThat("Wick").isEqualTo(pagedEmployees.getContent().get(2).getLastname());

  }

}
