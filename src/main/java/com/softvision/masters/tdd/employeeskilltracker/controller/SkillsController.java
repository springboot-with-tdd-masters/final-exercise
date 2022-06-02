package com.softvision.masters.tdd.employeeskilltracker.controller;

import com.softvision.masters.tdd.employeeskilltracker.model.Skill;
import com.softvision.masters.tdd.employeeskilltracker.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
public class SkillsController {

    @Autowired
    SkillService skillService;

    @GetMapping("{id}")
    public ResponseEntity<Skill> get(@PathVariable long id) {
        return new ResponseEntity<>(skillService.getById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Skill> create(@RequestBody Skill skill) {
        return new ResponseEntity<>(skillService.create(skill), new HttpHeaders(), HttpStatus.CREATED);
    }

    public ResponseEntity<Page<Skill>> getAll(@PageableDefault Pageable page) {
        return new ResponseEntity<>(skillService.getAll(page), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Skill>> getAll(@RequestParam(required = false) String description,
                                             @PageableDefault Pageable page) {
        if (description == null) {
            return getAll(page);
        }
        return new ResponseEntity<>(skillService.getDescriptionContaining(description, page), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        skillService.delete(id);
        return new ResponseEntity<>(id, new HttpHeaders(), HttpStatus.OK);
    }
}
