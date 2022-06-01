package com.example.employeeTDD.repository;

import com.example.employeeTDD.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Page<Skill> findByEmployeeId(Long skillId, Pageable pageable);
    Optional<Skill> findByIdAndEmployeeId(Long id, Long skillId);
}
