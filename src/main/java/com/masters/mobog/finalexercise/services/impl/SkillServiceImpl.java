package com.masters.mobog.finalexercise.services.impl;

import com.masters.mobog.finalexercise.adapters.SkillAdapter;
import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.entities.Skill;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.repositories.SkillRepository;
import com.masters.mobog.finalexercise.services.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository repository;
    private final EmployeeRepository employeeRepository;
    private final SkillAdapter adapter;
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
            mapped.setId(found.get().getId());
            return repository.save(mapped);
        }
        throw new FinalExerciseException(FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
    }

    @Override
    public Skill updateEmployeeSkill(Long employeeId, Long skillId, EmployeeSkillRequest skill) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            Optional<Skill> foundSkill = repository.findById(skillId);
            if(foundSkill.isPresent()){
                Skill existingSkill = foundSkill.get();
                Skill mapped = this.adapter.mapToSkill(skill);
                existingSkill.setDescription(mapped.getDescription());
                existingSkill.setDuration(mapped.getDuration());
                existingSkill.setLastUsed(mapped.getLastUsed());
                mapped.setEmployee(found.get());
                return repository.save(mapped);
            }
            throw new FinalExerciseException(FinalExerciseExceptionsCode.SKILL_NOT_FOUND_EXCEPTION);
        }
        throw new FinalExerciseException(FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
    }

    @Override
    public Page<Skill> getAllEmployeeSkills(Long employeeId, Pageable page) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            return repository.findAllByEmployeeId(employeeId, page);
        }
        throw new FinalExerciseException(FinalExerciseExceptionsCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
    }

    @Override
    public void deleteEmployeeSkill(Long employeeId, Long skillId) {
        Optional<Employee> found = this.employeeRepository.findById(employeeId);
        if(found.isPresent()){
            Optional<Skill> foundSkill = this.repository.findById(skillId);
            foundSkill.ifPresent(this.repository::delete);
        } else {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.SKILL_NOT_FOUND_EXCEPTION);
        }

    }
}
