package com.example.finalexercise.controller;

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

import com.example.finalexercise.model.dto.SkillDto;
import com.example.finalexercise.model.dto.SkillRequest;
import com.example.finalexercise.service.SkillService;

@RequestMapping("/employees")
@RestController
public class SkillController {
	
	@Autowired
	private SkillService skillService;

	@PostMapping("/{employeeId}/skills")
	public SkillDto addSkill(@PathVariable Long employeeId, @RequestBody SkillRequest request) {
		return skillService.addSkill(request, employeeId);
	}

	@GetMapping("/{employeeId}/skills")
	public Page<SkillDto> getSkillsOfEmployee(@PathVariable Long employeeId, Pageable page) {
		return skillService.getSkills(employeeId, page);
	}
	
	@PutMapping("/{employeeId}/skills/{skillId}")
	public SkillDto updateSkill(@PathVariable Long employeeId, @PathVariable Long skillId, 
			@RequestBody SkillRequest request) {
		return skillService.updateSkill(skillId, employeeId, request);
	}
	
	@DeleteMapping("/{employeeId}/skills/{skillId}")
	public void deleteSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
		skillService.deleteSkill(skillId, employeeId);
	}	
	
}
