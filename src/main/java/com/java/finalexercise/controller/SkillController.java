package com.java.finalexercise.controller;

import com.java.finalexercise.errorhandler.EmployeeNotFoundException;
import com.java.finalexercise.errorhandler.SkillNotFoundException;
import com.java.finalexercise.model.Skill;
import com.java.finalexercise.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SkillController {

    @Autowired
    private SkillService skillService;

    @DeleteMapping("/employees/{employeeId}/skills/{skillId}")
    public ResponseEntity delete(@PathVariable Long employeeId,
                                 @PathVariable Long skillId) throws SkillNotFoundException {
        return new ResponseEntity<>(skillService.delete(employeeId, skillId), HttpStatus.OK);
    }

    @PostMapping("/employees/{employeeId}/skills")
    public ResponseEntity save(@PathVariable Long employeeId,
                               @RequestBody Skill skill)
            throws EmployeeNotFoundException {
        return new ResponseEntity<>(skillService.save(employeeId, skill), HttpStatus.OK);
    }

    @PutMapping("/employees/{employeeId}/skills/{skillId}")
    private ResponseEntity<Skill> put(@PathVariable Long employeeId,
                                     @PathVariable Long skillId,
                                      @RequestBody Skill skill) throws
            SkillNotFoundException, EmployeeNotFoundException{
        return new ResponseEntity<>(skillService.update(employeeId, skillId, skill), HttpStatus.OK);
    }

    @GetMapping("/employees/{employeeId}/skills")
    public ResponseEntity find(Long employeeId,
            @PageableDefault(page = 0, size = 20)
                                   @SortDefault.SortDefaults({
                                           @SortDefault(sort = "description",
                                                   direction = Sort.Direction.DESC)
                                   })
                                           Pageable pageable) {
        return new ResponseEntity<>( skillService.find(employeeId, pageable), HttpStatus.OK);
    }
}
