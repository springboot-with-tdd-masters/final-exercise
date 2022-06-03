package com.example.finalexercise.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.finalexercise.model.Employees;

@DataJpaTest
public class EmployeesRepositoryTest {
	
	@Autowired
	EmployeesRepository repository;
	
	@Test
	@DisplayName("Save - should save Employee")
	public void shouldSaveAnEmployee() {
		
		Employees employee = new Employees();
		employee.setCreatedDate(new Date());
		employee.setLastModifiedDate(new Date());
		employee.setFirstName("Gerald");
		employee.setLastName("Estrada");
		
		Employees savedEmployee = repository.save(employee);
		
		assertThat(savedEmployee).extracting("id", "firstName", "lastName").containsExactly(1L, "Gerald", "Estrada");
		
	}

}
