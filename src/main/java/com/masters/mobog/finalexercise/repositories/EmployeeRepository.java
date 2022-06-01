package com.masters.mobog.finalexercise.repositories;

import com.masters.mobog.finalexercise.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
