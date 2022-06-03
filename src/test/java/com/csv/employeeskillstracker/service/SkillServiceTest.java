package com.csv.employeeskillstracker.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.model.Skill;
import com.csv.employeeskillstracker.repository.SkillRepository;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

	@Mock
	SkillRepository skillRepository;

	@InjectMocks
	SkillService skillService = new SkillServiceImpl();

	@Test
	@DisplayName("Should Get All Skills of an Employee")
	void should_Get_All_Skills() {
		Employee employee = new Employee("Charles", "Alcachupas");
		
		Skill skill1 = new Skill();
		skill1.setDescription("Java Springboot");
		skill1.setDuration(2);
		skill1.setLastUsed(LocalDate.now());
		skill1.setEmployee(employee);
		
		Skill skill2 = new Skill();
		skill2.setDescription("ReactJs");
		skill2.setDuration(4);
		skill2.setLastUsed(LocalDate.now());
		skill2.setEmployee(employee);
		
		List<Skill> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);

		List<Skill> sortedAccounts = skills.stream().sorted(Comparator.comparing(Skill::getDescription))
				.collect(Collectors.toList());
		Page<Skill> skillPage = new PageImpl<>(sortedAccounts);

		Pageable pageable = PageRequest.of(0, 5, Sort.by("description").ascending());
		when(skillRepository.findAllSkillsByEmployeeId(pageable, 1L)).thenReturn(skillPage);

		Page<Skill> actualSkillPage = skillService.getAllSkills(pageable, 1L);

		assertAll(
				() -> assertEquals(1, actualSkillPage.getTotalPages()),
				() -> assertEquals(2, actualSkillPage.getTotalElements()),
				() -> assertEquals(2, actualSkillPage.getNumberOfElements()),
				() -> assertEquals("Java Springboot", actualSkillPage.getContent().get(0).getDescription()),
				() -> assertEquals(2, actualSkillPage.getContent().get(0).getDuration()),
				() -> assertEquals(LocalDate.now(), actualSkillPage.getContent().get(0).getLastUsed()),
				() -> assertEquals("ReactJs", actualSkillPage.getContent().get(1).getDescription()),
				() -> assertEquals(4, actualSkillPage.getContent().get(1).getDuration()),
				() -> assertEquals(LocalDate.now(), actualSkillPage.getContent().get(1).getLastUsed())
				);
		
	}

}
