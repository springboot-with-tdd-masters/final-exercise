package com.java.master.finals.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.java.master.finals.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	

	@Test
	@DisplayName("Should save Employee to db")
	public void shouldSaveBook_Test() {
		
		//arrange
		Employee employee = new Employee("Jerymy", "Gomez");
		
		//act
		Employee savedBook = employeeRepo.save(employee);
		
		//assert
		assertThat(savedBook).extracting("firstName", "lastName").containsExactly("Jerymy", "Gomez");
	}

}
