package com.csv.employeeskillstracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Skill;

public interface SkillService {

	public Page<Skill> getAllSkills(Pageable pageable, Long employeeId);
	
	public Skill createUpdate(Long employeeId, Long skillId, Skill skill) throws RecordNotFoundException;
	
	public void deleteSkill(Long employeeId, Long skillId) throws RecordNotFoundException;
}
