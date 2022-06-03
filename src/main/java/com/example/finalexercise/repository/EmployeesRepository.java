package com.example.finalexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.finalexercise.model.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long>{

}
