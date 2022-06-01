package com.masters.mobog.finalexercise.repositories;

import com.masters.mobog.finalexercise.entities.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Page<Skill> findAll(Pageable pageable);
    Page<Skill> findAllByEmployeeId(Long employeeId, Pageable pageable);
}
