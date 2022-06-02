package com.softvision.masters.tdd.employeeskilltracker.service;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Employee create(Employee employee);
    Page<Employee> getAll(Pageable pageable);
    Page<Employee> getNameContaining(String name, Pageable page);
    Employee getById(long id);
    Skill createSkill(long id, Skill skill);
    void delete(long id);
}
