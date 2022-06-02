package com.masters.mobog.finalexercise.services;

import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {
    Skill addSkillToEmployee(Long employeeId, EmployeeSkillRequest skill);
    Skill updateEmployeeSkill(Long employeeId, Long skillId, EmployeeSkillRequest skill);
    Page<Skill>  getAllEmployeeSkills(Long employeeId, Pageable page);
    void deleteEmployeeSkill(Long employeeId, Long skillId);
}
