package com.example.finalexercise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalexercise.model.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>{
	
	Page<Skills> findByEmployeesId(Long id, Pageable page);
	Page<Skills> findByEmployeesIdAndId(Long employeeId, Long id, Pageable page);
	Skills findByEmployeesIdAndId(Long employeeId, Long id);
}
