package com.example.finalexercise.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.Skill;

@EnableJpaAuditing
@DataJpaTest
public class SkillRepositoryTest {

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Test
	@DisplayName("Should add Skill to Employee entity")
	public void saveSkill() {
		Employee employee = new Employee();
	    employee.setFirstname("Christian");
	    employee.setLastname("Perez");
	     
	    Employee savedEmployee = employeeRepository.save(employee);
	    
	    Skill skill = new Skill();
	    skill.setDescription("Problem Solving");
	    skill.setEmployee(savedEmployee);
	    
	    savedEmployee.addSkill(skill);

	    Skill savedSkill = skillRepository.save(skill);

	    assertEquals(savedEmployee.getId(), savedSkill.getEmployee().getId());
	    assertEquals(skill.getDescription(), savedSkill.getDescription());
	    assertNotNull(savedSkill.getCreatedDate());
	    assertNotNull(savedSkill.getId());
	}
}
