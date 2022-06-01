package com.masters.mobog.finalexercise.services.impl;

import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.repositories.SkillRepository;
import com.masters.mobog.finalexercise.services.SkillService;

public class SkillServiceImpl implements SkillService {
    private SkillRepository repository;
    private EmployeeRepository employeeRepository;
    public SkillServiceImpl(SkillRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }
}
