package com.softvision.masters.tdd.employeeskilltracker.repository;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByFirstnameContainingOrLastnameContaining(String firstnameConditionInfix, String lastnameconditionInfix, Pageable pageable);
}
