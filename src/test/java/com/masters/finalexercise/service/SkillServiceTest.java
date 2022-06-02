package com.masters.finalexercise.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;
import com.masters.finalexercise.repository.SkillRepository;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

	@Mock
	private SkillRepository skillRepository;
	
	@Mock
	private EmployeeService employeeService;
	
	@InjectMocks
	private SkillServiceImpl skillService;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void saveSkillToEmployee() throws RecordNotFoundException {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    emp.setFirstname("dummyfname");
	    emp.setLastname("dummylname");
	    
	    when(employeeService.findById(1L)).thenReturn(emp);
	    
		Skill skill = new Skill();
	    skill.setDescription("Java");
	    skill.setDuration(7);
	    skill.setLastUsed(LocalDate.now());
	    skill.setEmployee(emp);
	    
	    when(skillRepository.save(any(Skill.class))).thenReturn(skill);
	    
	    Skill actualSkill = skillService.save(skill, 1L);
	    assertNotNull(actualSkill);
	    
	}
	
	@Test
	public void findAllSkillByEmployee() throws RecordNotFoundException {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    emp.setFirstname("dummyfname");
	    emp.setLastname("dummylname");
	    
	    List<Skill> skillList = new ArrayList<Skill>();
	    
		Skill skill1 = new Skill();
		skill1.setDescription("Java");
		skill1.setDuration(7);
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployee(emp);
		
		Skill skill2 = new Skill();
		skill2.setDescription("Java");
		skill2.setDuration(7);
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployee(emp);
		
		skillList.add(skill1);
		skillList.add(skill2);
		
		final Page<Skill> skillsPage = new PageImpl<>(skillList);
		Pageable paging = PageRequest.of(0, 10, Sort.by("id"));
	    when(skillRepository.findAllByEmployeeId(1L, paging)).thenReturn(skillsPage);
	    
	    Page<Skill> actualSkill = skillService.findAll(1L, 0, 10, "id");
	    assertNotNull(actualSkill);
	    assertEquals(2, actualSkill.getSize());
	    
	}
	
	@Test
	public void findSkillByIdByEmployee() throws RecordNotFoundException {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    emp.setFirstname("dummyfname");
	    emp.setLastname("dummylname");
	    
		Skill skill = new Skill();
		skill.setDescription("Java");
		skill.setDuration(7);
		skill.setLastUsed(LocalDate.now());
		skill.setEmployee(emp);
		
	    when(skillRepository.findByIdAndEmployeeId(1L, 1L)).thenReturn(Optional.of(skill));
	    
	    Skill actualSkill = skillService.findById(1L, 1L);
	    assertNotNull(actualSkill);
	    
	}
	
	@Test
	public void deleteSkill() throws RecordNotFoundException {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    emp.setFirstname("dummyfname");
	    emp.setLastname("dummylname");
	    
		Skill skill = new Skill();
		skill.setDescription("Java");
		skill.setDuration(7);
		skill.setLastUsed(LocalDate.now());
		skill.setEmployee(emp);
		
		when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
  
	    Skill actualSkill = skillService.delete(1L);
	    assertNotNull(actualSkill);
	    
	}
	
}
