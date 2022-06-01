package com.masters.mobog.finalexercise.services.impl;

import com.masters.mobog.finalexercise.adapters.SkillAdapter;
import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.repositories.SkillRepository;
import com.masters.mobog.finalexercise.services.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class SkillServiceImpl implements SkillService {
    private SkillRepository repository;
    private EmployeeRepository employeeRepository;
    private SkillAdapter adapter;
    public SkillServiceImpl(SkillRepository repository, EmployeeRepository employeeRepository,
                            SkillAdapter adapter) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.adapter = adapter;
    }

    @Override
    public Skill addSkillToEmployee(Long employeeId, EmployeeSkillRequest skill) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            Skill mapped = this.adapter.mapToSkill(skill);
            mapped.setEmployee(found.get());
            return repository.save(mapped);
        }
        throw new NullPointerException();
    }

    @Override
    public Skill updateEmployeeSkill(Long employeeId, Long skillId, EmployeeSkillRequest skill) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        Skill mapped = this.adapter.mapToSkill(skill);
        if(found.isPresent()){
            Optional<Skill> foundSkill = repository.findById(skillId);
            if(foundSkill.isPresent()){
                mapped.setEmployee(found.get());
                return repository.save(mapped);
            }
        }
        throw new NullPointerException();
    }

    @Override
    public Page<Skill> getAllEmployeeSkills(Long employeeId, Pageable page) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            return repository.findAllByEmployeeId(employeeId, page);
        }
        throw new NullPointerException();
    }

    @Override
    public void deleteEmployeeSkill(Long employeeId, Long skillId) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            Optional<Skill> foundSkill = this.repository.findById(skillId);
            if(foundSkill.isPresent()){
                this.repository.delete(foundSkill.get());
            }
        } else {
            throw new NullPointerException();
        }

    }
}
