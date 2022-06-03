package com.java.finalexercise.service;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.model.Employee;
import com.java.finalexercise.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) throws EmployeeNotFoundException {
        findById(employee.getId());
        return employeeRepository.save(employee);
    }

    @Override
    public Long delete(Long id) throws EmployeeNotFoundException{
        findById(id);

        employeeRepository.deleteById(id);
        return id;
    }

    @Override
    public Employee findById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> optionalCurrentEmployee = employeeRepository.findById(id);

        if (optionalCurrentEmployee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        return optionalCurrentEmployee.get();
    }

    @Override
    public Page<Employee> find(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }
}
