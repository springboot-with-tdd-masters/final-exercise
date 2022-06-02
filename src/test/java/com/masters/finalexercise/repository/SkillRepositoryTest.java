package com.masters.finalexercise.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;

@DataJpaTest
public class SkillRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SkillRepository skillRepository;
	
	@Test
	public void testSaveSkillToEmployee() {
		
	     Employee employee = new Employee();
	     employee.setFirstname("dummyfname");
	     employee.setLastname("dummylname");
	     
	     Employee savedEmployee = employeeRepository.save(employee);
	     
	     Skill skill = new Skill();
	     skill.setLastUsed(LocalDate.now());
	     skill.setDescription("dummyDescription");
	     skill.setDuration(7);
	     skill.setEmployee(savedEmployee);
	     
	     Skill savedSkill = skillRepository.save(skill);

	     assertNotNull(savedSkill);
	     assertEquals("dummyDescription", savedSkill.getDescription());
	}
	
	@Test
	public void testFindAllSkillByEmployee() {
		
	     Employee employee = new Employee();
	     employee.setFirstname("dummyfname");
	     employee.setLastname("dummylname");
	     
	     Employee savedEmployee = employeeRepository.save(employee);
	     
	     Skill skill1 = new Skill();
	     skill1.setLastUsed(LocalDate.now());
	     skill1.setDescription("dummyDescription1");
	     skill1.setDuration(7);
	     skill1.setEmployee(savedEmployee);
	     
	     Skill skill2 = new Skill();
	     skill2.setLastUsed(LocalDate.now());
	     skill2.setDescription("dummyDescription2");
	     skill2.setDuration(7);
	     skill2.setEmployee(savedEmployee);
	     
	     skillRepository.save(skill1);
	     skillRepository.save(skill2);
	     
	     Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
	     Page<Skill> skills = skillRepository.findAllByEmployeeId(1L, paging);

	     assertNotNull(skills);
	     assertEquals(10, skills.getSize());
	}
	
	@Test
	public void testFindSkillByIdEmployee() {
		
	     Employee employee = new Employee();
	     employee.setFirstname("dummyfname");
	     employee.setLastname("dummylname");
	     
	     Employee savedEmployee = employeeRepository.save(employee);
	     
	     Skill skill1 = new Skill();
	     skill1.setLastUsed(LocalDate.now());
	     skill1.setDescription("dummyDescription1");
	     skill1.setDuration(7);
	     skill1.setEmployee(savedEmployee);
	     
	     skillRepository.save(skill1);
	     
	     Skill skill = skillRepository.findByIdAndEmployeeId(skill1.getId(), savedEmployee.getId()).get();

	     assertNotNull(skill);
	     assertEquals("dummyDescription1", skill.getDescription());
	}
	
}
