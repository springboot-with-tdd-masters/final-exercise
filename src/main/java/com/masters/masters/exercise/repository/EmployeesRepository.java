package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.Employees;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<Employees,Long> {
}
