package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import com.masters.masters.exercise.model.SkillsDto;
import com.masters.masters.exercise.repository.EmployeesRepository;
import com.masters.masters.exercise.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillsServiceImpl {

    @Autowired
    private SkillsRepository repository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public Skills saveOrUpdateSkills(SkillsDto dto, Long employeeId,Long skillsId) throws RecordNotFoundException {
        Skills skills;
        if(skillsId != null){
            Optional<Employees> employeesOptional = employeesRepository.findById(employeeId);
            if(employeesOptional.isEmpty()){
                throw  new RecordNotFoundException("Employee with id " + employeeId + " does not exist");
            }else{
                Optional<Skills> skillsOptional = repository.findById(skillsId);
                if(skillsOptional.isEmpty()){
                    skills = new Skills();
                    skills.setEmployees(employeesOptional.get());
                }else{
                    skills = skillsOptional.get();
                }
            }
        }else{
            Optional<Employees> employeesOptional = employeesRepository.findById(employeeId);
            if(employeesOptional.isEmpty()){
                throw  new RecordNotFoundException("Employee with id " + employeeId + " does not exist");
            }else{
                skills = new Skills();
                skills.setEmployees(employeesOptional.get());
            }
        }
        skills.setDescription(dto.getDescription());
        skills.setDuration(dto.getDuration());
        skills.setLastUsed(dto.getLastUsed());
        return repository.save(skills);
    }

    public Page<Skills> findAllSkills(Long id, Pageable pageable) throws RecordNotFoundException {
        Optional<Employees> employeesOptional = employeesRepository.findById(id);
        if(employeesOptional.isEmpty()){
            throw  new RecordNotFoundException("Employee with id " + id + " does not exist");
        }else{
            return repository.findByEmployees(employeesOptional.get(),pageable);
        }
    }

    public void deleteSkill(Long employeeId,Long skillId) throws RecordNotFoundException {
        Optional<Employees> employeesOptional = employeesRepository.findById(employeeId);
        if(employeesOptional.isEmpty()){
            throw  new RecordNotFoundException("Employee with id " + employeeId + " does not exist");
        }else{
            Optional<Skills> skillsOptional = repository.findById(skillId);
            if(skillsOptional.isEmpty()){
                throw  new RecordNotFoundException("Skill with id " + skillId + " does not exist");
            }else{
                repository.delete(skillsOptional.get());
            }
        }
    }
}
