package com.example.employee.services.impl;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.exceptions.codes.EmployeeExceptionCode;
import com.example.employee.repositories.EmployeeRepository;
import com.example.employee.services.EmployeeService;
import com.example.employee.services.adapters.EmployeeAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeAdapter employeeAdapter;

    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EmployeeAdapter employeeAdapter) {
        this.employeeRepository = employeeRepository;
        this.employeeAdapter = employeeAdapter;
    }

    @Override
    public Page<Employee> findAll(Pageable pageRequest) {

        final Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageRequest);

        return employeeEntityPage.map(employeeAdapter::convert);
    }

    @Override
    public Employee findById(Long id) {

        final EmployeeEntity employeeEntity = findEntityExits(id);

        return employeeAdapter.convert(employeeEntity);
    }

    @Override
    public Employee create(EmployeeRequest employeeRequest) {

        final EmployeeEntity employeeEntity = employeeAdapter.convert(employeeRequest);

        employeeRepository.save(employeeEntity);

        return employeeAdapter.convert(employeeEntity);
    }

    @Override
    public Employee update(Long id, EmployeeRequest employeeRequest) {

        final EmployeeEntity employeeToUpdate = findEntityExits(id);

        employeeToUpdate.setFirstName(employeeRequest.getFirstName());
        employeeToUpdate.setLastName(employeeRequest.getLastName());

        employeeRepository.save(employeeToUpdate);

        return employeeAdapter.convert(employeeToUpdate);
    }

    @Override
    public void delete(Long id) {

        final EmployeeEntity employeeToDelete = findEntityExits(id);

        employeeRepository.delete(employeeToDelete);
    }

    private EmployeeEntity findEntityExits(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));
    }
}
