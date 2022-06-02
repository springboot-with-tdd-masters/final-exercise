package com.masters.masters.exercise.controller;

import com.masters.masters.exercise.exception.RecordNotFoundException;
import com.masters.masters.exercise.model.EmployeeDto;
import com.masters.masters.exercise.model.Employees;
import com.masters.masters.exercise.model.Skills;
import com.masters.masters.exercise.model.SkillsDto;
import com.masters.masters.exercise.services.SkillsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class SkillsController {

    @Autowired
    private SkillsServiceImpl service;

    @PostMapping("/{id}/skills")
    public ResponseEntity<Skills> saveOrUpdateSkills(@PathVariable Long id,@RequestBody SkillsDto request) throws RecordNotFoundException {
        Skills response = service.saveOrUpdateSkills(request,id,null);
        return new ResponseEntity<Skills>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{id}/skills/{skillsId}")
    public ResponseEntity<Skills> saveOrUpdateSkills(@PathVariable Long id,@PathVariable Long skillsId
            ,@RequestBody SkillsDto request) throws RecordNotFoundException {
        Skills response = service.saveOrUpdateSkills(request,id,skillsId);
        return new ResponseEntity<Skills>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}/skills")
    public ResponseEntity<Page<Skills>> findSkillsByEmployeeId(@PathVariable Long id,Pageable pageRequest) throws RecordNotFoundException {
        Page<Skills>list= service.findAllSkills(id,pageRequest);
        return new ResponseEntity<Page<Skills>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}/skills/{skillsId}")
    public ResponseEntity deleteSkills(@PathVariable Long id,@PathVariable Long skillsId) throws RecordNotFoundException {
        service.deleteSkill(id,skillsId);
        return new ResponseEntity(new HttpHeaders(), HttpStatus.OK);
    }
}
