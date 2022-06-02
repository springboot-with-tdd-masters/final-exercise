package com.example.demo.controller;

import com.example.demo.dto.SkillDto;
import com.example.demo.model.Skill;
import com.example.demo.dto.SkillRequest;
import com.example.demo.service.SkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employees")
public class SkillController {

    @Autowired
    private SkillServiceImpl skillServiceImpl;

    @PostMapping("/{employeeId}/skills")
    public ResponseEntity<SkillDto> createNewSkill(@PathVariable Long employeeId, @Valid @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillServiceImpl.createNewSkill(employeeId, skillRequest));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Skill> readSkillById(@PathVariable Long id) {
//        return ResponseEntity.ok(skillServiceImpl.readSkillById(id));
//    }

    @GetMapping("/{employeeId}/skills")
    private ResponseEntity<Page<SkillDto>> readAllSkillsOfEmployee(@PathVariable Long employeeId, @PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                        Pageable pageable) {
        return ResponseEntity.ok(skillServiceImpl.readAllSkills(employeeId, pageable));
    }

    @PutMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Long employeeId, @PathVariable Long skillId, @Valid @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillServiceImpl.updateSkill(employeeId, skillId, skillRequest));
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
        skillServiceImpl.deleteSkillById(employeeId, skillId);
        return ResponseEntity.ok().build();
    }
}
