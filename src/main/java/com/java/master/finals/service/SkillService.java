package com.java.master.finals.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.master.finals.model.Skill;

public interface SkillService {

	Skill createUpdate(Long employeeId, Long skillId, Skill skill);
	
	void delete(Long employeeId, Long skillId);

	Page<Skill> getAll(long employeeId, Pageable pageRequest);
}
