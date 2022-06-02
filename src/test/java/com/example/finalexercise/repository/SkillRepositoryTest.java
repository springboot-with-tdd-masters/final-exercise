package com.example.finalexercise.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	    assertAll(
			() -> assertEquals(savedEmployee.getId(), savedSkill.getEmployee().getId()),
			() -> assertEquals(skill.getDescription(), savedSkill.getDescription()),			   
			() -> assertNotNull(savedSkill.getCreatedDate()),
			() -> assertNotNull(savedSkill.getId())
		);	
	}
	
	@Test
	@DisplayName("Should update skill")
	public void updateSkill() {
		Employee employee = new Employee();
	    employee.setFirstname("Christian");
	    employee.setLastname("Perez");
	     
	    Employee savedEmployee = employeeRepository.save(employee);
	    
	    Skill skill = new Skill();
	    skill.setDescription("Problem Solving");
	    skill.setEmployee(savedEmployee);
	    
	    savedEmployee.addSkill(skill);

	    Skill savedSkill = skillRepository.save(skill);
	    
	    //update skill
	    savedSkill.setDescription("Java");
	    Skill updatedSkill = skillRepository.save(savedSkill);

	    assertEquals("Java", updatedSkill.getDescription());
	}
	
	@Test
	@DisplayName("Should fetch all skill of employee")
	public void getAllSkills() {
		Employee employee = new Employee();
	    employee.setFirstname("Christian");
	    employee.setLastname("Perez");
	     
	    Employee savedEmployee = employeeRepository.save(employee);
	    
	    Skill skill = new Skill();
	    skill.setDescription("Java");
	    skill.setEmployee(savedEmployee);

	    Skill skill2 = new Skill();
	    skill2.setDescription("C#");
	    skill2.setEmployee(savedEmployee);

	    savedEmployee.addSkill(skill);
	    savedEmployee.addSkill(skill2);

	    skillRepository.save(skill);
	    skillRepository.save(skill2);

	 	Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "description"));

	    Page<Skill> pagedSkills = skillRepository.findByEmployeeId(savedEmployee.getId(), pageable);

	    assertAll(
		 	() -> assertEquals(2, pagedSkills.getTotalElements()),
			() -> assertEquals("Java", pagedSkills.getContent().get(0).getDescription()),			   
			() -> assertEquals("C#", pagedSkills.getContent().get(1).getDescription())			    
		 );	
	}
	
	@Test
	@DisplayName("Should delete skill")
	public void shouldDeleteSkill() {
		Employee employee = new Employee();
	    employee.setFirstname("Christian");
	    employee.setLastname("Perez");
	     
	    Employee savedEmployee = employeeRepository.save(employee);
	    
	    Skill skill = new Skill();
	    skill.setDescription("Problem Solving");
	    skill.setEmployee(savedEmployee);
	    
	    savedEmployee.addSkill(skill);

	    Skill savedSkill = skillRepository.save(skill);
	    
	    skillRepository.delete(savedSkill);
	     
	    Optional<Skill> skillOpt = skillRepository.findById(savedSkill.getId());
	   
	    assertTrue(skillOpt.isEmpty());
	}
}
