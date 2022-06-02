package com.example.finalexercise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.finalexercise.model.dto.SkillDto;
import com.example.finalexercise.model.dto.SkillRequest;

public interface SkillService {
	SkillDto addSkill(SkillRequest request, Long employeeId);
	SkillDto updateSkill(Long skillId, Long employeeId, SkillRequest request);
	Page<SkillDto> getSkills(Long employeeId, Pageable pageable);
	void deleteSkill(Long skillId, Long employeeId);
}
