package com.finalexam.skills.repository;

import com.finalexam.skills.model.entity.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

  Page<Skill> findAll(Pageable pageable);

}
