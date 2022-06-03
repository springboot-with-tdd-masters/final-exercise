package com.example.finalexercise.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.finalexercise.model.Employees;
import com.example.finalexercise.model.Skills;

@DataJpaTest
public class SkillsRepositoryTest {
	
	@Autowired
	SkillsRepository repository;
	
	@Autowired
	EmployeesRepository employeesRepository;
	
	@Test
	@DisplayName("Retrieve - should retrieve Skills of an Employee")
	public void shouldRetrieveSkillsOfAnEmployee() {
		
		Employees employeeEntity = new Employees();
		employeeEntity.setId(1L);
		employeeEntity.setFirstName("Gerald");
		employeeEntity.setLastName("Estrada");
		employeeEntity.setCreatedDate(new Date());
		employeeEntity.setLastModifiedDate(new Date());
		
		Skills skill1 = new Skills();
		skill1.setId(1L);
		skill1.setDuration(1);
		skill1.setDescription("Java");
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployees(employeeEntity);
		
		Skills skill2 = new Skills();
		skill2.setId(2L);
		skill2.setDuration(2);
		skill2.setDescription("React");
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployees(employeeEntity);
		
		Page<Skills> pageResp = new PageImpl<>(List.of(skill1, skill2));
		Pageable page = PageRequest.of(0, 5);
		employeesRepository.save(employeeEntity);
		repository.saveAll(pageResp);
		
		assertNotNull(repository.findByEmployeesId(1L, page));
		assertThat(repository.findByEmployeesId(1L, page)).hasSize(2);
	}
	
}