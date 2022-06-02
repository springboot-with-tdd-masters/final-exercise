package com.example.finalexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalexercise.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
