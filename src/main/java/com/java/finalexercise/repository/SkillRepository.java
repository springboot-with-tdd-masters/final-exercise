package com.java.finalexercise.repository;

import com.java.finalexercise.model.Employee;
import com.java.finalexercise.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s from Skill as s where s.employee.id = :employeeId AND s.id = :id")
    public Optional<Skill> findByIdAndEmployeeId(Long id, Long employeeId);

    @Query("SELECT s from Skill as s where s.employee.id = :employeeId")
    public Page<Skill> findSkills(Long employeeId, Pageable page);
}
