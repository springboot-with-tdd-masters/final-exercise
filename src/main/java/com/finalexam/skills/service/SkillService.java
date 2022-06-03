package com.finalexam.skills.service;

import com.finalexam.skills.model.dto.request.SkillRequestDto;
import com.finalexam.skills.model.dto.response.SkillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {

  SkillDto save(SkillRequestDto skillRequestDto, long employeeId);
  SkillDto getSkillByEmployeeIdById(long employeeId, long skillId);
  void deleteByEmployeeIdBySkillId(long employeeId, long skillId);
  SkillDto update(SkillRequestDto updatePayload, long employeeId);
  Page<SkillDto> getSkills(Pageable pageable);
}
