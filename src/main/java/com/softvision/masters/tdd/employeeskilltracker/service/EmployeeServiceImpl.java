package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.model.exception.RecordNotFoundException;
import com.softvision.masters.tdd.employeeskilltracker.repository.EmployeeRepository;
import com.softvision.masters.tdd.employeeskilltracker.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SkillRepository skillRepository;

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAll(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        if (!employees.hasContent()) {
            throw new RecordNotFoundException();
        }
        return employees;
    }

    @Override
    public Employee getById(long id) {
        return employeeRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Skill createSkill(long id, Skill skill) {
        return employeeRepository.findById(id).map(employee -> {
            skill.setEmployee(employee);
            return skillRepository.save(skill);
        }).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void delete(long id) {
        employeeRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> getNameContaining(String name, Pageable pageable) {
        Page<Employee> employees = employeeRepository.findByFirstnameContainingOrLastnameContaining(name, name, pageable);
        if (!employees.hasContent()) {
            throw new RecordNotFoundException();
        }
        return employees;
    }
}
