package com.java.master.finals.controller;

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

import com.java.master.finals.model.Skill;
import com.java.master.finals.service.SkillService;

@RestController
@RequestMapping("/employees")
public class SkillsController {

	@Autowired
	private SkillService skillService;

	@PostMapping("/{employeeId}/skills")
	public Skill createUpdate(@PathVariable Long employeeId, @RequestBody Skill skill) {
		return skillService.createUpdate(employeeId, 0l, skill);
	}

	@PutMapping("/{employeeId}/skills/{skillId}")
	public Skill createUpdate(@PathVariable Long employeeId, @PathVariable Long skillId, @RequestBody Skill skill) {
		return skillService.createUpdate(employeeId, skillId, skill);
	}

	@DeleteMapping("/{employeeId}/skills/{skillId}")
	public void delete(@PathVariable Long employeeId, @PathVariable Long skillId) {
		skillService.delete(employeeId, skillId);
	}

	@GetMapping("/{employeeId}/skills")
	public Page<Skill> getAllSkill(@PathVariable Long employeeId, Pageable page) {
		return skillService.getAll(employeeId, page);
	}
}
