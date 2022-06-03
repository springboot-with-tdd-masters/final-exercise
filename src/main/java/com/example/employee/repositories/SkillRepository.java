package com.example.employee.repositories;

import com.example.employee.domain.entities.SkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    Page<SkillEntity> findAllByEmployeeEntityId(Long employeeId, Pageable pageRequest);

    Optional<SkillEntity> findByEmployeeEntityIdAndId(Long employeeId, Long id);

}
