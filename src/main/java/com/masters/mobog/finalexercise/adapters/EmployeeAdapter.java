package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeAdapter {

    public Employee mapToEmployee(EmployeeRequest employeeRequest) {
        if (Optional.ofNullable(employeeRequest.getFirstname()).isPresent() &&
               Optional.ofNullable(employeeRequest.getLastname()).isPresent()) {
            Employee emp = new Employee();
            emp.setFirstname(employeeRequest.getFirstname());
            emp.setLastname(employeeRequest.getLastname());
            return emp;
        } else {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.MAPPING_EXCEPTION);
        }
    }
}
