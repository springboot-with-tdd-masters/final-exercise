package com.java.finalexercise.service;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.errorhandler.SkillNotFoundException;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.model.Skill;
import com.java.finalexercise.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Skill save(Long employeeId, Skill skill) throws EmployeeNotFoundException {
        Employee employee = employeeService.findById(employeeId);
        skill.setEmployee(employee);

        return skillRepository.save(skill);
    }

    @Override
    public Skill update(Long employeeId, Long id, Skill skill) throws SkillNotFoundException {

        Skill currentSkill = findById(employeeId, id);
        currentSkill.setDescription(skill.getDescription());
        currentSkill.setDuration(skill.getDuration());
        currentSkill.setLastUsed(skill.getLastUsed());

        return currentSkill;
    }

    @Override
    public Long delete(Long employeeId, Long id) throws SkillNotFoundException {

        findById(employeeId, id);

        skillRepository.deleteById(id);

        return id;
    }

    @Override
    public Skill findById(Long employeeId, Long id) throws SkillNotFoundException {
        Optional<Skill> optionalSkill = skillRepository.findByIdAndEmployeeId(id, employeeId);

        if(optionalSkill.isEmpty()) {
            throw new SkillNotFoundException();
        }

        return optionalSkill.get();
    }

    @Override
    public Page<Skill> find(Long employeeId, Pageable pageable) {
        return skillRepository.findSkills(employeeId, pageable);
    }
}
