package com.masters.mobog.finalexercise.services.impl;

import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> found = repository.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new NullPointerException();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Employee update(Employee employee) {
        Optional<Employee> found = repository.findById(employee.getId());
        if (found.isPresent()) {
            return repository.save(employee);
        }
        throw new NullPointerException();
    }

    @Override
    public void delete(Employee employee) {
        Optional<Employee> found = repository.findById(employee.getId());
        if (found.isPresent()) {
            repository.delete(employee);
        } else {
            throw new NullPointerException();
        }
    }
}
