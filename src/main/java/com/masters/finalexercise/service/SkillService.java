package com.masters.finalexercise.service;

import com.masters.finalexercise.common.service.BaseService;
import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;
import org.springframework.data.domain.Page;

public interface SkillService extends BaseService<Skill> {

    public Page<Skill> findAll(Long id, Integer pageNo, Integer pageSize, String sortBy) throws RecordNotFoundException;

    public Skill findById(Long skillId, Long employeeId) throws RecordNotFoundException;

    public Skill save(Skill entity, Long employeeId) throws RecordNotFoundException;
}
