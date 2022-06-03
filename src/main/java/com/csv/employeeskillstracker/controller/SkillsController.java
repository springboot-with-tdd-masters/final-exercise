package com.csv.employeeskillstracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Skill;
import com.csv.employeeskillstracker.service.SkillService;

@RestController
@RequestMapping("/employees")
public class SkillsController {

	@Autowired
	private SkillService skillService;

	@PostMapping("/{employee_id}/skills")
	public Skill createSkill(@PathVariable("employee_id") Long employeeId, @RequestBody Skill skill) throws RecordNotFoundException {
		return skillService.createUpdate(employeeId, -1L, skill);
	}

	@PutMapping("/{employee_id}/skills/{skill_id}")
	public Skill updateSkill(@PathVariable("employee_id") Long employeeId, @PathVariable("skill_id") Long skillId, @RequestBody Skill skill) throws RecordNotFoundException {
		return skillService.createUpdate(employeeId, skillId, skill);
	}

	@GetMapping("/{employee_id}/skills")
	public Page<Skill> getAllSkills(@PathVariable("employee_id") Long employeeId, Pageable page) {
		return skillService.getAllSkills(page, employeeId);
	}
	
	@DeleteMapping("/{employee_id}/skills/{skill_id}")
	public void deleteSkill(@PathVariable("employee_id") Long employeeId, @PathVariable("skill_id") Long skillId) throws RecordNotFoundException {
		skillService.deleteSkill(employeeId, skillId);
	}
}