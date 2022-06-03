package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.helper.CustomErrorResponse;
import com.advancejava.finalexercise.model.Employee;
import com.advancejava.finalexercise.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends CustomErrorResponse implements EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Override
    public Employee addEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public Employee getEmployee(Long id) {
        if (repository.findById(id).isEmpty()){
            throw badRequest("NotFound");
        }
        return repository.findById(id).get();
    }

    @Override
    public List<Employee> getAllEmployees() {
        if (repository.findAll().isEmpty()){
            throw badRequest("NotFound");
        }
        return repository.findAll();
    }

    @Override
    public Employee updateEmployee(Employee request) {
        if (repository.findById(request.getId()).isPresent()){
            return repository.save(request);
        }
        throw badRequest("NotFound");
    }

    @Override
    public String deleteEmployee(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw badRequest("NotFound");
        }
        repository.deleteById(id);
        return "Success";
    }

    @Override
    public Page<Employee> getWithPaginationAndSort(int page, int limit, String field, String order) {
        Sort sOrder = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(field).ascending() : Sort.by(field).descending();
        return repository.findAll(PageRequest.of(page, limit, sOrder));
    }
}
