package com.example.demo.repository;

import com.example.demo.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Page<Skill> findByEmployeeId(Long employeeId, Pageable page);

    List<Skill> findByEmployeeId(Long employeeId);
}
