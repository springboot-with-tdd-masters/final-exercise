package com.java.master.finals.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.java.master.finals.model.Employee;
import com.java.master.finals.model.Skill;

@DataJpaTest
public class SkillRepositoryTest {

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	@DisplayName("Should get all skills of an employee")
	public void shouldGetAllSkills_Test() {

		Employee employee = new Employee("Jerymy", "Gomez");
		employeeRepository.save(employee);

		Skill skillData = new Skill();

		skillData.setId(1l);
		skillData.setDescription("Java");
		skillData.setDuration(2);
		skillData.setLastUsed(LocalDate.now());
		skillData.setEmployee(employee);
		skillRepository.save(skillData);

		Pageable pageRequest = PageRequest.of(0, 1, Sort.by("description").ascending());
		 Page<Skill> pagedSkill = skillRepository.findAllByEmployeeId(1l, pageRequest);

		assertAll(() -> assertEquals(1, pagedSkill.getTotalPages()),
				() -> assertEquals(1, pagedSkill.getTotalElements()),
				() -> assertEquals(1, pagedSkill.getNumberOfElements()),
				() -> assertEquals("Java", pagedSkill.getContent().get(0).getDescription()),
				() -> assertEquals(2, pagedSkill.getContent().get(0).getDuration()),
				() -> assertEquals(LocalDate.now(), pagedSkill.getContent().get(0).getLastUsed()));
	}
}
