package com.java.finalexercise.service;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.errorhandler.SkillNotFoundException;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SkillService {

    public Skill save(Long employeeId, Skill skill) throws EmployeeNotFoundException;

    public Skill update(Long employeeId, Long id, Skill skill) throws SkillNotFoundException;

    public Long delete(Long employeeId, Long id) throws SkillNotFoundException;

    public Skill findById(Long employeeId, Long id) throws SkillNotFoundException;

    public Page<Skill> find(Long employeeId, Pageable pageable);
}
