package com.masters.masters.exercise.services;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.EmployeeDto;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeesServiceImpl {

    @Autowired
    private EmployeesRepository repo;

    public Employees findById(Long id) throws RecordNotFoundException {
        Optional<Employees> employeesOptional = repo.findById(id);
        if(employeesOptional.isEmpty()){
            throw new RecordNotFoundException("Employee with id " + id + " does not exists");
        }else{
            return employeesOptional.get();
        }
    }

    public Employees createOrUpdateEmployee(EmployeeDto dto){
        Employees employees;
        if(dto.getId() != null){
            Optional<Employees> employeesOptional = repo.findById(dto.getId());
            if(employeesOptional.isEmpty()){
                employees = new Employees();
            }else{
                employees = employeesOptional.get();
            }

        }else{
            employees  = new Employees();
        }
        employees.setFirstName(dto.getFirstName());
        employees.setLastName(dto.getLastName());
        return repo.save(employees);
    }

    public Page<Employees> findAllEmployees(Pageable pageRequest){
        return repo.findAll(pageRequest);
    }

    public void deleteEmployee(Long id) throws RecordNotFoundException {
        Optional<Employees> employeesOptional = repo.findById(id);
        if(employeesOptional.isEmpty()){
            throw new RecordNotFoundException("Employee with id " + id + " does not exists");
        }else{
            repo.delete(employeesOptional.get());
        }
    }
}
