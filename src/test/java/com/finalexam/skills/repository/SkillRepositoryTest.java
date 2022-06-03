package com.finalexam.skills.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.finalexam.skills.model.entity.Employee;
import com.finalexam.skills.model.entity.Skill;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@DataJpaTest
public class SkillRepositoryTest {

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  SkillRepository skillRepository;

  @Test
  @DisplayName("Should save skills with the correct details")
  public void saveSkill() {

    Employee newEmployee = new Employee("John", "Wick");

    Employee savedEmployee = employeeRepository.save(newEmployee);

    Skill newSkill = new Skill("Krav maga",10,newEmployee, LocalDate.now());

    Skill savedSkill = skillRepository.save(newSkill);

    assertThat(savedSkill.getDescription()).isEqualTo("Krav maga");
    assertThat(savedSkill.getDuration()).isEqualTo(10);
    assertThat(savedSkill.getEmployee()).isEqualTo(newEmployee);
    assertThat(savedSkill.getLastUsed()).isNotNull();
    assertThat(savedSkill.getCreatedAt()).isNotNull();
    assertThat(savedSkill.getUpdatedAt()).isNotNull();
    assertThat(savedSkill.getId()).isNotNull();

  }

  @Test
  @DisplayName("Should return page object containing 3 skills")
  public void getPageObjectWith3SkillContent() {

    Employee newEmployee = new Employee("John", "Wick");
    Employee savedEmployee = employeeRepository.save(newEmployee);

    Skill skill1 = new Skill("Magnificat",10,newEmployee, LocalDate.now());
    Skill skill2 = new Skill("Asura Strike",4,newEmployee, LocalDate.now());
    Skill skill3 = new Skill("Bash",2,newEmployee, LocalDate.now());

    skillRepository.save(skill1);
    skillRepository.save(skill2);
    skillRepository.save(skill3);

    Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.ASC, "description"));
    Page<Skill> pagedSkill = skillRepository.findAll(pageable);

    assertThat(pagedSkill.getTotalPages()).isEqualTo(1);
    assertThat(pagedSkill.getTotalElements()).isEqualTo(3);
    assertThat(pagedSkill.getNumberOfElements()).isEqualTo(3);
    assertThat(pagedSkill.getContent().size()).isEqualTo(3);
    assertThat(pagedSkill.getContent().get(0).getDescription()).isEqualTo("Asura Strike");
    assertThat(pagedSkill.getContent().get(1).getDescription()).isEqualTo("Bash");
    assertThat(pagedSkill.getContent().get(2).getDescription()).isEqualTo("Magnificat");
  }
}
