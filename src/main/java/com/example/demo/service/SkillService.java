package com.example.demo.service;

import com.example.demo.dto.SkillDto;
import com.example.demo.model.Skill;
import com.example.demo.dto.SkillRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {
    SkillDto createNewSkill(Long employeeId, SkillRequest skillRequest);
    SkillDto readSkillById(Long id);
    Page<SkillDto> readAllSkills(Long employeeId, Pageable pageable);
    SkillDto updateSkill(Long employeeId, Long skillId, SkillRequest skillRequest);
    void deleteSkillById(Long employeeId, Long skillId);
}
