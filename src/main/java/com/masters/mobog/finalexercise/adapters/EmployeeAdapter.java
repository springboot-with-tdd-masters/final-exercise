package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.RegisterEmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;

public class EmployeeAdapter {

    public Employee mapToEmployee(RegisterEmployeeRequest employeeRequest){
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
