package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    SkillRepository skillRepository;

    @Override
    public Page<Skill> getAllByEmployee(long id, Pageable pageable) {
        Page<Skill> skills = skillRepository.findByEmployeeId(id, pageable);
        if (!skills.hasContent()) {
            throw new RecordNotFoundException();
        }
        return skills;
    }

    @Override
    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Page<Skill> getAll(Pageable page) {
        Page<Skill> skills = skillRepository.findAll(page);
        if (!skills.hasContent()) {
            throw new RecordNotFoundException();
        }
        return skills;
    }

    @Override
    public Skill getById(long id) {
        return skillRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Skill update(Skill skill) {
        return skillRepository.findById(skill.getId())
                .map(s -> skillRepository.save(s))
                .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Page<Skill> getDescriptionContaining(String description, Pageable page) {
        Page<Skill> skills = skillRepository.findByDescriptionContaining(description, page);
        if (!skills.hasContent()) {
            throw new RecordNotFoundException();
        }
        return skills;
    }

    @Override
    public void delete(long id) {
        if (skillRepository.existsById(id)) {
            skillRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
