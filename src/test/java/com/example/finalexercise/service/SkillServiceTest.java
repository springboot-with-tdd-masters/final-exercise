package com.example.finalexercise.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.finalexercise.exception.SkillNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.Skill;
import com.example.finalexercise.model.dto.SkillDto;
import com.example.finalexercise.model.dto.SkillRequest;
import com.example.finalexercise.repository.SkillRepository;
import com.example.finalexercise.util.DateUtil;

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
	@DisplayName("Should add Skill to Employee")
	public void saveSkill() {
	    Employee emp = new Employee();
	    emp.setId(1L);
	    
	    when(employeeService.findEmployee(1L))
	    	.thenReturn(emp);
	    
		Skill skill = new Skill();
	    skill.setId(1L);
	    skill.setDescription("Java");
	    skill.setDuration(10);
	    skill.setLastUsed(DateUtil.parse("2022-06-02"));
	    skill.setEmployee(emp);
	    
	    when(skillRepository.save(any(Skill.class)))
	    	.thenReturn(skill);
	    
	    SkillDto expected = SkillDto.convertToDto(skill);
		    
	    SkillRequest request = SkillRequest.request("Java", 10, "2022-06-02");
	    	
	    SkillDto actual = skillService.addSkill(request, 1L);

	    assertAll(
			() -> assertEquals(request.getDescription(), actual.getDescription()),
			() -> assertEquals(request.getDuration(), actual.getDuration()),			   
			() -> assertEquals(request.getLastUsed(), actual.getLastUsed()),			   
			() -> assertNotNull(actual.getId())
		);	
	}
	
	@Test
	@DisplayName("Should update skill")
	public void updateSkill() {
		Employee emp = new Employee();
	    emp.setId(1L);
	    
	    when(employeeService.findEmployee(1L))
	    	.thenReturn(emp);
	    
		Skill skill = new Skill();
	    skill.setId(1L);
	    skill.setDescription("Java");
	    skill.setDuration(10);
	    skill.setLastUsed(DateUtil.parse("2022-06-02"));
	    skill.setEmployee(emp);
	    
	    Optional skillOpt = Optional.of(skill);
	    when(skillRepository.findById(1L))
	    	.thenReturn(skillOpt);
	 	    
	    SkillRequest request = SkillRequest.request("Java 8", 10, "2022-06-02");
	    	
	    SkillDto actual = skillService.updateSkill(1L, 1L, request);

	    assertEquals("Java 8", actual.getDescription());
	}
	
	@Test
	@DisplayName("Should get all skills of an employee with paging and sorting")
	public void getAllSkills() {
		Employee emp = new Employee();
	    emp.setId(1L);
	
		Skill skill = new Skill();
		skill.setId(1L);
		skill.setDuration(10);
		skill.setLastUsed(LocalDate.now());
	    skill.setDescription("Java");
	    skill.setEmployee(emp);
	    
	    Skill skill2 = new Skill();
	    skill2.setId(2L);
	    skill2.setDuration(10);
	    skill2.setLastUsed(LocalDate.now());
	    skill2.setDescription("C#");
	    skill2.setEmployee(emp);

	    Page<Skill> pagedSkills = new PageImpl(Arrays.asList(skill, skill2));
	    
	 	Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "description"));
	    when(skillRepository.findByEmployeeId(1L, pageable))
	    	.thenReturn(pagedSkills);

	    Page<SkillDto> actualResponse = skillService.getSkills(1L, pageable);
	    
	    assertAll(
	    		() -> assertEquals(2, actualResponse.getNumberOfElements()),
			    () -> assertEquals(1L, actualResponse.getContent().get(0).getId()),
			    () -> assertEquals("Java", actualResponse.getContent().get(0).getDescription()),
			    () -> assertEquals(2L, actualResponse.getContent().get(1).getId()),
			    () -> assertEquals("C#", actualResponse.getContent().get(1).getDescription())
		    );	
	}
	
	@Test
	@DisplayName("Should delete skill")
	public void deleteSkill() {
		Skill skill = new Skill();
	    skill.setId(1L);
	    skill.setDescription("Java");
	    
	    Optional skillOpt = Optional.of(skill);
	    when(skillRepository.findById(1L))
	    	.thenReturn(skillOpt);
	 	    	    	
	    skillService.deleteSkill(1L, 1L);

	    verify(skillRepository).delete(any(Skill.class));
	}
	
	@Test
	@DisplayName("Should throw SkillNotFoundException when no skill found")
	public void shouldThrowExceptionWhenNoSkillFound() {
		when(skillRepository.findById(1L))
	    	.thenReturn(Optional.empty());
		assertThrows(SkillNotFoundException.class, () -> skillService.deleteSkill(1L, 1L));
	}
}
