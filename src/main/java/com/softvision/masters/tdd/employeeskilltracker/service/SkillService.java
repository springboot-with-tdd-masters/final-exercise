package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkillService {
    Page<Skill> getAllByEmployee(long id, Pageable pageable);

    Skill create(Skill skill);

    Page<Skill> getAll(Pageable page);

    Skill getById(long id);

    Skill update(Skill skill);

    Page<Skill> getDescriptionContaining(String description, Pageable page);

    void delete(long id);
}
