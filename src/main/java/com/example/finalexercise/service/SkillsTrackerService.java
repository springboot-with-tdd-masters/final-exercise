package com.example.finalexercise.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.finalexercise.model.Employees;
import com.example.finalexercise.model.Skills;
import com.example.finalexercise.request.EmployeesRequest;
import com.example.finalexercise.request.SkillsRequest;
import com.example.finalexercise.response.EmployeesResponse;
import com.example.finalexercise.response.SkillsResponse;

public interface SkillsTrackerService {
	
	EmployeesResponse createOrUpdateEmployee(EmployeesRequest employeesRequest);
	Page<EmployeesResponse> getAllEmployees(Pageable page);
	EmployeesResponse getEmployeeById(Long id);
	SkillsResponse addSkill(Long id, SkillsRequest skillsRequest);
	SkillsResponse updateSkill(Long employeeId, Long skillsId, SkillsRequest skillsRequest);
	Page<SkillsResponse> getSkillsByEmployeeId(Long id, Pageable page);
	Page<SkillsResponse> getSkill(Long employeeId, Long skillsId, Pageable page);
	Employees deleteEmployee(Long id);
	Skills deleteSkill(Long employeeId, Long skillsId);
}
