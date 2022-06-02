package com.masters.masters.exercise.repository;

import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends PagingAndSortingRepository<Skills,Long> {

    Page<Skills> findByEmployees(Employees employees, Pageable pageRequest);
}
