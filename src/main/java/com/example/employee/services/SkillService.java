package com.example.employee.services;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {

    Page<Skill> findAllSkillsOf(Long employeeId, Pageable pageRequest);

    Skill findSkillOf(Long employeeId, Long id);

    Skill createSkillOf(Long employeeId, SkillRequest skillRequest);

    Skill updateSkillOf(Long employeeId, Long id, SkillRequest skillRequest);

    void deleteSkillOf(Long employeeId, Long id);
}
