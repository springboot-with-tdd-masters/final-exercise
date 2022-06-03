package com.example.finalexercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.finalexercise.request.EmployeesRequest;
import com.example.finalexercise.request.SkillsRequest;
import com.example.finalexercise.response.EmployeesResponse;
import com.example.finalexercise.response.SkillsResponse;
import com.example.finalexercise.service.SkillsTrackerService;

@Controller
@RequestMapping(value = "/employees")
public class SkillsTrackerController {
	
	@Autowired
	SkillsTrackerService service;
	
	@PostMapping
	public ResponseEntity<EmployeesResponse> createOrUpdateEmployee(@RequestBody EmployeesRequest employeesRequest) {
		EmployeesResponse newEmployee = service.createOrUpdateEmployee(employeesRequest);
		
		return new ResponseEntity<EmployeesResponse>(newEmployee, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<EmployeesResponse>> getAllEmployees(Pageable page) {
		Page<EmployeesResponse> employees = service.getAllEmployees(page);
		
		return new ResponseEntity<Page<EmployeesResponse>>(employees, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeesResponse> getEmployeedById(@PathVariable("id") Long id) {
		EmployeesResponse employee = service.getEmployeeById(id);
		
		return new ResponseEntity<EmployeesResponse>(employee, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/skills")
	public ResponseEntity<SkillsResponse> addSkill(@PathVariable("id") Long id, @RequestBody SkillsRequest skillsRequest) {
		SkillsResponse newSkill = service.addSkill(id, skillsRequest);
		
		return new ResponseEntity<SkillsResponse>(newSkill, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("/{employeeId}/skills/{skillsId}")
	public ResponseEntity<SkillsResponse> updateSkill(@PathVariable("employeeId") Long employeeId, @PathVariable("skillsId") Long skillsId, @RequestBody SkillsRequest skillsRequest) {
		SkillsResponse newSkill = service.updateSkill(employeeId, skillsId, skillsRequest);
		
		return new ResponseEntity<SkillsResponse>(newSkill, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/skills")
	public ResponseEntity<Page<SkillsResponse>> getSkillsByEmployeeId(@PathVariable("id") Long id, Pageable page) {
		Page<SkillsResponse> skills = service.getSkillsByEmployeeId(id, page);
		
		return new ResponseEntity<Page<SkillsResponse>>(skills, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{employeeId}/skills/{skillsId}")
	public ResponseEntity<Page<SkillsResponse>> getSkill(@PathVariable("employeeId") Long employeeId, @PathVariable("skillsId") Long skillsId, Pageable page) {
		Page<SkillsResponse> skill = service.getSkill(employeeId, skillsId, page);
		
		return new ResponseEntity<Page<SkillsResponse>>(skill, new HttpHeaders(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		service.deleteEmployee(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{employeeId}/skills/{skillsId}")
	public ResponseEntity<Void> deleteSkill(@PathVariable("employeeId") Long employeeId, @PathVariable("skillsId") Long skillsId) {
		service.deleteSkill(employeeId, skillsId);
		
		return ResponseEntity.noContent().build();
	}

}
