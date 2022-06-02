package com.masters.finalexercise.service;

import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.Skill;
import com.masters.finalexercise.model.UserEntity;
import com.masters.finalexercise.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Page<Skill> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return skillRepository.findAll(paging);
    }

    @Override
    public Page<Skill> findAll(Long id, Integer pageNo, Integer pageSize, String sortBy) throws RecordNotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return skillRepository.findAllByEmployeeId(id, paging);
    }

    @Override
    public Skill findById(Long skillId, Long employeeId) throws RecordNotFoundException {
        Optional<Skill> skill = skillRepository.findByIdAndEmployeeId(skillId, employeeId);
        return skill.map(obj -> {
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + skillId));
    }

    @Override
    public Skill findById(Long id) throws RecordNotFoundException {
        Optional<Skill> skill = skillRepository.findById(id);
        return skill.map(obj -> {
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }

    @Override
    public Skill save(Skill entity, Long employeeId) throws RecordNotFoundException {
        Optional<Skill> savedSkill = null;

        Employee employee = employeeService.findById(employeeId);

        if(entity.getId() != null) {
            savedSkill = skillRepository.findById(entity.getId());
        }

        if(savedSkill != null) {
            Skill skill = savedSkill.get();
            skill.setDescription(entity.getDescription());
            skill.setLastUsed(entity.getLastUsed());
            skill.setDuration(entity.getDuration());
            skill.setEmployee(employee);
            return skillRepository.save(skill);
        } else {
            entity.setEmployee(employee);
            return skillRepository.save(entity);
        }

    }

    @Override
    public Skill save(Skill entity) {
        return null;
    }

    @Override
    public Skill delete(Long id) throws RecordNotFoundException {
        Optional<Skill> skill = skillRepository.findById(id);
        return skill.map(obj -> {
            skillRepository.deleteById(id);
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }

}
