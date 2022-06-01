package com.masters.mobog.finalexercise.adapters;


import com.masters.mobog.finalexercise.dto.EmployeeRequest;
import com.masters.mobog.finalexercise.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Employee2AdapterTest {

    private EmployeeAdapter adapter;
    @BeforeEach
    void setup(){
        adapter = new EmployeeAdapter();
    }

    @Test
    @DisplayName("should map successfully")
    void shouldMapSuccessfully(){
        EmployeeRequest request = new EmployeeRequest();
        request.setLastname("Jane");
        request.setFirstname("Doe");
        Employee actual = adapter.mapToEmployee(request);
        assertEquals("Jane", actual.getLastname());
        assertEquals("Doe", actual.getFirstname());
    }
}
