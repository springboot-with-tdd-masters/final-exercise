package com.java.master.finals.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.master.finals.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

	Page<Skill> findAllByEmployeeId(Long employeeId, Pageable page);

}
