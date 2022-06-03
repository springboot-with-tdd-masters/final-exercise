package com.example.employee.controllers;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.services.SkillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/employees/{employeeId:\\d+}/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public Page<Skill> getAll(@PathVariable Long employeeId, Pageable pageRequest) {
        return skillService.findAllSkillsOf(employeeId, pageRequest);
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Skill> get(@PathVariable Long employeeId, @PathVariable Long id) {

        final Skill skill = skillService.findSkillOf(employeeId, id);

        return ResponseEntity.ok(skill);
    }

    @PostMapping
    public ResponseEntity<Skill> create(@PathVariable Long employeeId, @Valid @RequestBody SkillRequest skillRequest) {

        final Skill skill = skillService.createSkillOf(employeeId, skillRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(skill);
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<Skill> update(
            @PathVariable Long employeeId,
            @Valid @RequestBody SkillRequest skillRequest,
            @PathVariable Long id) {

        final Skill skill = skillService.updateSkillOf(employeeId, id, skillRequest);

        return ResponseEntity.ok(skill);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<String> delete(@PathVariable Long employeeId, @PathVariable Long id) {

        skillService.deleteSkillOf(employeeId, id);

        return ResponseEntity.ok("Skill successfully deleted");
    }

}
