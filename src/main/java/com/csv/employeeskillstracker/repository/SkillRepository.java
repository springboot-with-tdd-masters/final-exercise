package com.csv.employeeskillstracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csv.employeeskillstracker.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{
	public Page<Skill> findAllSkillsByEmployeeId(Pageable page, Long employeId);
}
