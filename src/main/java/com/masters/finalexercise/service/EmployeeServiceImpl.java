package com.masters.finalexercise.service;

import com.masters.finalexercise.exceptions.RecordNotFoundException;
import com.masters.finalexercise.model.Employee;
import com.masters.finalexercise.model.UserEntity;
import com.masters.finalexercise.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return employeeRepository.findAll(paging);
    }

    @Override
    public Employee findById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(obj -> {
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }

    @Override
    public Employee save(Employee entity) {
        Optional<Employee> employee = null;
        if(entity.getId() != null) {
            employee = employeeRepository.findById(entity.getId());
        }

        if(employee != null) {
            Employee emp = employee.get();
            emp.setFirstname(entity.getFirstname());
            emp.setLastname(entity.getLastname());
            return employeeRepository.save(emp);
        } else {
            return employeeRepository.save(entity);
        }
    }

    @Override
    public Employee delete(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(obj -> {
            employeeRepository.deleteById(id);
            return obj;
        }).orElseThrow(() -> new RecordNotFoundException("No Record found with id: " + id));
    }

}
