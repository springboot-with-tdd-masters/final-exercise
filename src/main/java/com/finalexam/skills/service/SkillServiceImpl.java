package com.finalexam.skills.service;

import com.finalexam.skills.exception.EmployeeNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  SkillRepository skillRepository;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  SkillMapper skillMapper;


  @Override
  public SkillDto save(SkillRequestDto skillRequestDto, long employeeId) {

    Employee employeeEntity =
        employeeRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException());

    Skill newSkill = new Skill(skillRequestDto.getDescription(), skillRequestDto.getDuration(),
        employeeEntity,
        LocalDate.parse(skillRequestDto.getLastUsed(), DateTimeFormatter.ISO_LOCAL_DATE));

    Skill savedSkill = skillRepository.save(newSkill);

    return skillMapper.convert(savedSkill);

  }

  @Override
  public SkillDto getSkillByEmployeeIdById(long employeeId, long skillId) {

    employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException());

    Skill skillEntity =
        skillRepository.findById(skillId)
            .orElseThrow(() -> new SkillNotFoundException());
    return skillMapper.convert(skillEntity);
  }

  @Override
  public void deleteByEmployeeIdBySkillId(long employeeId, long skillId) {

    employeeRepository.findById(employeeId)
        .orElseThrow(() -> new EmployeeNotFoundException());

    Skill skillEntity =
        skillRepository.findById(skillId)
            .orElseThrow(() -> new SkillNotFoundException());
    skillRepository.delete(skillEntity);
  }

  @Override
  public SkillDto update(SkillRequestDto updatePayload, long employeeId) {

    employeeRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException());

    Skill skillEntity =
        skillRepository.findById(updatePayload.getId())
            .orElseThrow(() -> new SkillNotFoundException());

    skillEntity.setDuration(updatePayload.getDuration());
    skillEntity.setDescription(updatePayload.getDescription());

    Skill savedSkill = skillRepository.save(skillEntity);

    return skillMapper.convert(savedSkill);
  }

  @Override
  public Page<SkillDto> getSkills(Pageable pageable) {
    Page<Skill> skillEntityPage = skillRepository.findAll(pageable);
    return skillMapper.convert(skillEntityPage);
  }
}
