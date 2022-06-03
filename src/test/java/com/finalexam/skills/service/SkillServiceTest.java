package com.finalexam.skills.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.finalexam.skills.exception.SkillNotFoundException;
import com.finalexam.skills.model.dto.mapper.SkillMapper;
import com.finalexam.skills.model.dto.request.SkillRequestDto;
import com.finalexam.skills.model.dto.response.SkillDto;
import com.finalexam.skills.model.entity.Employee;
import com.finalexam.skills.model.entity.Skill;
import com.finalexam.skills.repository.EmployeeRepository;
import com.finalexam.skills.repository.SkillRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class SkillServiceTest {


  @Mock
  private SkillRepository skillRepository;

  @Mock
  private EmployeeRepository employeeRepository;

  @Mock
  private SkillMapper skillMapper;

  @InjectMocks
  private SkillService skillService = new SkillServiceImpl();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Save - save new skill")
  public void saveSkillTest() {

    SkillRequestDto skillRequestDto = new SkillRequestDto("Bowling Bash", 2, "2022-06-02");
    Employee employee = new Employee("John", "Wick");
    employee.setId(1L);

    Skill newSkillEntity = new Skill();
    newSkillEntity.setId(1L);
    newSkillEntity.setDescription("Bowling Bash");
    newSkillEntity.setDuration(2);
    newSkillEntity.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    newSkillEntity.setEmployee(employee);

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

    Skill mockedSkillEntityFromRepo = mock(Skill.class);
    when(skillRepository.save(newSkillEntity)).thenReturn(mockedSkillEntityFromRepo);

    SkillDto mockedSavedSkill = mock(SkillDto.class);
    when(skillMapper.convert(mockedSkillEntityFromRepo)).thenReturn(mockedSavedSkill);

    SkillDto actualSkill = skillService.save(skillRequestDto, 1L);

    assertThat(actualSkill.getId()).isEqualTo(mockedSavedSkill.getId());
    assertThat(actualSkill.getDescription()).isEqualTo(mockedSavedSkill.getDescription());
    assertThat(actualSkill.getLastUsed()).isEqualTo(mockedSavedSkill.getLastUsed());
    assertThat(actualSkill.getEmployee()).isEqualTo(mockedSavedSkill.getEmployee());

    verify(employeeRepository).findById(1L);
    verify(skillRepository).save(newSkillEntity);
    verify(skillMapper).convert(mockedSkillEntityFromRepo);

  }

  @Test
  @DisplayName("Get skill details by id")
  public void getOneSkillById() {
    Employee mockedEmployee = mock(Employee.class);

    Skill skillEntity = new Skill();
    skillEntity.setId(1L);
    skillEntity.setDescription("Bowling Bash");
    skillEntity.setDuration(2);
    skillEntity.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    skillEntity.setEmployee(mockedEmployee);

    when(employeeRepository.findById(1L))
        .thenReturn(Optional.ofNullable(mockedEmployee));

    when(skillRepository.findById(1L)).thenReturn(Optional.of(skillEntity));

    SkillDto mockedSavedSkill = mock(SkillDto.class);
    when(skillMapper.convert(skillEntity)).thenReturn(mockedSavedSkill);

    SkillDto actualSkill = skillService.getSkillByEmployeeIdById(1L,1L);

    assertThat(actualSkill.getDescription()).isEqualTo(mockedSavedSkill.getDescription());
    assertThat(actualSkill.getDuration()).isEqualTo(mockedSavedSkill.getDuration());

    verify(employeeRepository).findById(1L);
    verify(skillRepository).findById(1L);
    verify(skillMapper).convert(skillEntity);

  }

  @Test
  @DisplayName("Should return skill not found, when passed with invalid skill id")
  public void getOneSkill_return_SkillNotFoundException() {

    Optional<Skill> skillOptional = Optional.empty();
    Employee mockedEmployee = mock(Employee.class);

    when(employeeRepository.findById(1L))
        .thenReturn(Optional.ofNullable(mockedEmployee));

    when(skillRepository.findById(1L)).thenReturn(skillOptional);

    assertThrows(SkillNotFoundException.class,
        () -> skillService.getSkillByEmployeeIdById(1L,1L));
  }

  @Test
  @DisplayName("Should delete skill successfully")
  public void deleteSkillTest() {

    Employee mockedEmployee = mock(Employee.class);

    Skill skillEntity = new Skill();
    skillEntity.setId(1L);
    skillEntity.setDescription("Bowling Bash");
    skillEntity.setDuration(2);
    skillEntity.setLastUsed(LocalDate.parse("2022-06-02", DateTimeFormatter.ISO_LOCAL_DATE));
    skillEntity.setEmployee(mockedEmployee);

    when(employeeRepository.findById(1L))
        .thenReturn(Optional.ofNullable(mockedEmployee));

    when(skillRepository.findById(1L)).thenReturn(Optional.of(skillEntity));

    skillService.deleteByEmployeeIdBySkillId(1L, skillEntity.getId());

    verify(employeeRepository).findById(1L);
    verify(skillRepository).delete(skillEntity);
    verify(skillRepository).findById(1L);

  }

  @Test
  @DisplayName("Should throw exception when deleting an skill while passing invalid id")
  public void deleteSkill_return_SkillNotFoundException() {
    Optional<Skill> expectedSkill = Optional.empty();
    Employee mockedEmployee = mock(Employee.class);
    when(employeeRepository.findById(1L))
        .thenReturn(Optional.ofNullable(mockedEmployee));

    when(skillRepository.findById(1L)).thenReturn(expectedSkill);

    assertThrows(SkillNotFoundException.class,
        () -> skillService.deleteByEmployeeIdBySkillId(1L,1L));
  }

  @Test
  @DisplayName("Should update skill first details and return updated value")
  public void updateSkillTest() {

    Employee mockedEmployee = mock(Employee.class);
    when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockedEmployee));

    Skill mockedCurrentSkillValue = mock(Skill.class);
    when(skillRepository.findById(anyLong())).thenReturn(Optional.of(mockedCurrentSkillValue));

    SkillRequestDto updatePayload = new SkillRequestDto(1L,"Bowling Bashher",12,"2022-06-02");

    Skill mockedSkillEntityFromRepo = mock(Skill.class);
    when(skillRepository.save(any(Skill.class))).thenReturn(mockedSkillEntityFromRepo);

    SkillDto mockedSavedSkill = mock(SkillDto.class);
    when(skillMapper.convert(mockedSkillEntityFromRepo)).thenReturn(mockedSavedSkill);

    SkillDto actualUpdatedSkill = skillService.update(updatePayload,1L);

    assertThat(actualUpdatedSkill.getDescription()).isEqualTo(mockedSavedSkill.getDescription());
    assertThat(actualUpdatedSkill.getDuration()).isEqualTo(mockedSavedSkill.getDuration());

    verify(employeeRepository).findById(anyLong());
    verify(skillRepository).findById(anyLong());
    verify(skillRepository).save(mockedCurrentSkillValue);
    verify(skillMapper).convert(mockedSkillEntityFromRepo);

  }

  @Test
  @DisplayName("Get page object containing 3 skill sorted by description descending")
  public void getPagedObjectContainingSkillsSortedByDescriptionDesc() {
    Employee newEmployee = new Employee("John", "Wick");

    Skill newSkill1 = new Skill("Krav maga1",10,newEmployee, LocalDate.now());
    Skill newSkill2 = new Skill("Krav maga2",10,newEmployee, LocalDate.now());
    Skill newSkill3 = new Skill("Krav maga3",10,newEmployee, LocalDate.now());


    Page<Skill> returnSkills = new PageImpl(
        Arrays.asList(newSkill1, newSkill2, newSkill3));

    Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "description"));

    when(skillRepository.findAll(pageable))
        .thenReturn(returnSkills);

    Page<SkillDto> mockedSkillDtoPage = mock(Page.class);
    when(skillMapper.convert(returnSkills)).thenReturn(mockedSkillDtoPage);

    Page<SkillDto> actualSkillDtoPage = skillService.getSkills(pageable);

    assertThat(actualSkillDtoPage.getTotalPages()).isEqualTo(mockedSkillDtoPage.getTotalPages());
    assertThat(actualSkillDtoPage.getTotalElements()).isEqualTo(mockedSkillDtoPage.getTotalElements());
    assertThat(actualSkillDtoPage.getNumberOfElements()).isEqualTo(mockedSkillDtoPage.getNumberOfElements());
    assertThat(actualSkillDtoPage.getContent()).isEqualTo(mockedSkillDtoPage.getContent());

    verify(skillRepository).findAll(pageable);
    verify(skillMapper).convert(returnSkills);
  }
}
