package com.softvision.masters.tdd.employeeskilltracker.repository;

import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Page<Skill> findByEmployeeId(long id, Pageable pageable);

    Page<Skill> findByDescriptionContaining(String conditionInfix, Pageable pageable);
}
