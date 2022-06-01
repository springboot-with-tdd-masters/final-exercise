package com.masters.mobog.finalexercise.services.impl;

import com.masters.mobog.finalexercise.adapters.EmployeeAdapter;
import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.repositories.EmployeeRepository;
import com.masters.mobog.finalexercise.services.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeAdapter adapter;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeAdapter adapter) {
        this.repository = repository;
        this.adapter = adapter;
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
    public Employee createEmployee(EmployeeRequest employee) {
        Employee mapped = adapter.mapToEmployee(employee);
        return repository.save(mapped);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public Employee update(Long id, EmployeeRequest employee) {
        Employee mapped = adapter.mapToEmployee(employee);
        Optional<Employee> found = repository.findById(id);
        if (found.isPresent()) {
            return repository.save(mapped);
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
