package com.finalexam.skills.model.dto.mapper;

import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.dto.response.SkillDto;
import com.finalexam.skills.model.entity.Employee;
import com.finalexam.skills.model.entity.Skill;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class SkillMapperImpl implements SkillMapper {

  @Override
  public SkillDto convert(Skill skillEntity) {
    return new SkillDto(skillEntity.getCreatedAt(), skillEntity.getUpdatedAt(), skillEntity.getId(),
        skillEntity.getDescription(), skillEntity.getDuration(), skillEntity.getLastUsed(),
        new EmployeeDto(skillEntity.getEmployee()));
  }

  @Override
  public Page<SkillDto> convert(Page<Skill> skillEntityPage) {
    Page<SkillDto> result = new PageImpl<>(convertToDtoList(skillEntityPage.getContent()),
        skillEntityPage.getPageable(), skillEntityPage.getSize());
    return result;
  }

  @Override
  public Skill convert(SkillDto skillDto) {
    Skill result = new Skill();
    result.setId(skillDto.getId());
    result.setDescription(skillDto.getDescription());
    result.setDuration(skillDto.getDuration());
    result.setLastUsed(skillDto.getLastUsed());
    result.setEmployee(
        new Employee(skillDto.getEmployee().getId(), skillDto.getEmployee().getFirstname(),
            skillDto.getEmployee().getLastname()));
    return result;
  }

  @Override
  public List<SkillDto> convertToDtoList(List<Skill> skillEntityList) {
    List<SkillDto> resultList = new ArrayList<>();
    for (Skill skill : skillEntityList) {
      resultList.add(convert(skill));
    }
    return resultList;
  }

  @Override
  public List<Skill> convertToEntityList(List<SkillDto> skillDtoList) {
    List<Skill> resultList = new ArrayList<>();
    for (SkillDto skill : skillDtoList) {
      resultList.add(convert(skill));
    }
    return resultList;
  }
}
