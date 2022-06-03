package com.finalexam.skills.model.dto.mapper;

import com.finalexam.skills.model.dto.response.SkillDto;
import com.finalexam.skills.model.entity.Skill;
import java.util.List;
import org.springframework.data.domain.Page;

public interface SkillMapper {

  SkillDto convert(Skill skillEntity);

  Page<SkillDto> convert(Page<Skill> skillEntityPage);

  Skill convert(SkillDto skillDto);

  List<SkillDto> convertToDtoList(List<Skill> skillEntityList);

  List<Skill> convertToEntityList(List<SkillDto> skillDtoList);

}
