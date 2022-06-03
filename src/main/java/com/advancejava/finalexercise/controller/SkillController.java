package com.advancejava.finalexercise.controller;

import com.advancejava.finalexercise.model.Skill;
import com.advancejava.finalexercise.model.dto.DTOResponse;
import com.advancejava.finalexercise.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class SkillController {

    final static Logger logger = Logger.getLogger("SkillController");

    @Autowired
    private SkillService service;

    @PostMapping("/employees/{id}/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill, @PathVariable Long id){
        logger.info("adding skill...");
        return new ResponseEntity<>(service.addSkill(skill,id), HttpStatus.CREATED);
    }

    @GetMapping("/skills/{id}")
    public Skill getSkill(@PathVariable Long id){
        logger.info(String.format("get id %d", id));
        return service.getSkill(id);
    }

    @GetMapping("/skills")
    public List<Skill> getSkills(){
        logger.info("get all skills...");
        return service.getAllSkills();
    }

    @PutMapping("/employees/{empId}/skills/{id}")
    public Skill updateSkill(@RequestBody Skill request,@PathVariable Long empId,@PathVariable Long id){
        logger.info("updating id"+id);

        return service.updateSkill(request,empId,id);
    }

    @DeleteMapping("/skills/{id}")
    public String deleteSkill(@PathVariable Long id) {
        logger.info(String.format("deleting id %d", id));
        return service.deleteSkill(id);
    }

    @GetMapping("/skills/page/{page}/size/{size}")
    private DTOResponse<Page<Skill>> getSkillsWithPaginationAndSort(
            @PathVariable int page, @PathVariable int size,
            @RequestParam String field, @RequestParam String order) {
        logger.info("Skills Page "+page+1);
        Page<Skill> skill = service.getWithPaginationAndSort(page, size, field,order);
        return new DTOResponse<>(skill.getSize(), skill);
    }

}
