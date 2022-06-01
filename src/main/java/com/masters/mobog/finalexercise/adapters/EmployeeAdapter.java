package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAdapter {

    public Employee mapToEmployee(EmployeeRequest employeeRequest){
        try {
            Employee emp = new Employee();
            emp.setFirstname(employeeRequest.getFirstname());
            emp.setLastname(employeeRequest.getLastname());
            return emp;
        } catch (Exception e){
            throw e;
        }
    }
}
