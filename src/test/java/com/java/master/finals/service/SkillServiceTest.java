package com.java.master.finals.service;

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

import com.java.master.finals.model.Employee;
import com.java.master.finals.model.Skill;
import com.java.master.finals.repository.SkillRepository;
import com.java.master.finals.service.impl.SkillServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SkillServiceTest {

	@Mock
	SkillRepository skillRepository;

	@InjectMocks
	SkillService skillService = new SkillServiceImpl();

	@Test
	@DisplayName("Get All - should get all skill by employee")
	void shouldGetAllSkills_Test() {
		Employee emp = new Employee();
		emp.setId(1L);

		Skill skill1 = new Skill();
		skill1.setId(1L);
		skill1.setDuration(10);
		skill1.setLastUsed(LocalDate.now());
		skill1.setDescription("Java");
		skill1.setEmployee(emp);

		Skill skill2 = new Skill();
		skill2.setId(2L);
		skill2.setDuration(10);
		skill2.setLastUsed(LocalDate.now());
		skill2.setDescription(".Net");
		skill2.setEmployee(emp);

		List<Skill> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);

		List<Skill> sortedAccounts = skills.stream().sorted(Comparator.comparing(Skill::getDescription))
				.collect(Collectors.toList());
		Page<Skill> skillPage = new PageImpl<>(sortedAccounts);

		Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "description"));
		when(skillRepository.findAllByEmployeeId(1L, pageable)).thenReturn(skillPage);

		Page<Skill> actualResponse = skillService.getAll(1L, pageable);

		assertAll(() -> assertEquals(2, actualResponse.getNumberOfElements()),
				() -> assertEquals(2L, actualResponse.getContent().get(0).getId()),
				() -> assertEquals(".Net", actualResponse.getContent().get(0).getDescription()),
				() -> assertEquals(1L, actualResponse.getContent().get(1).getId()),
				() -> assertEquals("Java", actualResponse.getContent().get(1).getDescription()));
	}

}
