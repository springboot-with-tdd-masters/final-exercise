package com.example.employee.services.adapters;

import com.example.employee.domain.dtos.Employee;
import com.example.employee.domain.dtos.requests.EmployeeRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.util.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAdapter {

    public EmployeeEntity convert(EmployeeRequest employeeRequest) {

        final EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setFirstName(employeeRequest.getFirstName());
        employeeEntity.setLastName(employeeRequest.getLastName());

        return employeeEntity;
    }

    public Employee convert(EmployeeEntity employeeEntity) {

        final Employee employee = new Employee();

        employee.setId(employeeEntity.getId());
        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setCreatedDate(DateUtils.format(employeeEntity.getCreatedDate()));
        employee.setLastModifiedDate(DateUtils.format(employeeEntity.getLastModifiedDate()));

        return employee;
    }

}
