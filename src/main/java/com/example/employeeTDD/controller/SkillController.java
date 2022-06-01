package com.example.employeeTDD.controller;

import com.example.employeeTDD.model.Skill;
import com.example.employeeTDD.repository.EmployeeRepository;
import com.example.employeeTDD.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.employeeTDD.exception.RecordNotFoundException;

@RestController
public class SkillController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SkillRepository skillRepository;

	@GetMapping("/employees/{employeeId}/skills")
	public Page<Skill> getAllSkillsByEmployeeId(@PathVariable(value = "employeeId") Long employeeId, Pageable pageable) {
		return skillRepository.findByEmployeeId(employeeId, pageable);
	}

	@PostMapping("/employees/{employeeId}/skills")
	public Skill createSkills(@PathVariable(value = "employeeId") Long employeeId, @RequestBody Skill skill)
			throws RecordNotFoundException {
		return employeeRepository.findById(employeeId).map(employee -> {
			skill.setEmployee(employee);
			return skillRepository.save(skill);
		}).orElseThrow(() -> new RecordNotFoundException("Employee id: " + employeeId + " not found!"));
	}

	@PutMapping("/employees/{employeeId}/skills/{skillId}")
	public Skill updateSkill(@PathVariable(value = "employeeId") Long employeeId, @PathVariable(value = "skillId") Long skillId,
							 @RequestBody Skill skillRequest) throws RecordNotFoundException {
		if (!employeeRepository.existsById(employeeId)) {
			throw new RecordNotFoundException("Employee id: " + employeeId + " not found!");
		}

		return skillRepository.findById(skillId).map(skill -> {
			skill.setDescription(skillRequest.getDescription());
			skill.setDuration(skillRequest.getDuration());
			return skillRepository.save(skill);
		}).orElseThrow(() -> new RecordNotFoundException("Skill id: " + skillId + " not found!"));
	}

	@DeleteMapping("/employees/{employeeId}/skills/{skillId}")
	public ResponseEntity<?> deleteSkill(@PathVariable Long employeeId, @PathVariable Long skillId)
			throws RecordNotFoundException {
		return skillRepository.findByIdAndEmployeeId(skillId, employeeId).map(skill -> {
			skillRepository.delete(skill);
			return ResponseEntity.ok().build();
		}).orElseThrow(
				() -> new RecordNotFoundException("Skill not found with id: " + skillId + " and employee id: " + employeeId));
	}

}