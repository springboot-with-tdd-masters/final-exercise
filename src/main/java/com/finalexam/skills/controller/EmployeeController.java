package com.finalexam.skills.controller;

import com.finalexam.skills.model.dto.request.EmployeeRequestDto;
import com.finalexam.skills.model.dto.request.SkillRequestDto;
import com.finalexam.skills.model.dto.response.EmployeeDto;
import com.finalexam.skills.model.dto.response.SkillDto;
import com.finalexam.skills.service.EmployeeService;
import com.finalexam.skills.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/employees")
@RestController
public class EmployeeController {

  @Autowired
  private SkillService skillService;

  @Autowired
  private EmployeeService employeeService;

  @PostMapping
  public EmployeeDto createOrUpdateEmployee(
      @RequestBody EmployeeRequestDto employeeRequestPayload) {
    if (employeeRequestPayload.getId() > 0) {
      return employeeService.update(employeeRequestPayload);
    } else {
      return employeeService.save(employeeRequestPayload);
    }
  }

  @GetMapping("/{id}")
  public EmployeeDto getEmployee(@PathVariable long id) {
    return employeeService.getEmployeeById(id);
  }

  @DeleteMapping("/{id}")
  public String deleteEmployee(@PathVariable long id) {
    employeeService.deleteEmployeeById(id);
    return "Employee successfully deleted id: " + id;
  }

  @GetMapping
  public Page<EmployeeDto> getEmployees(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "id,desc") String[] sort) {

    String sortName = sort[0];
    Sort.Direction direction = Sort.Direction.fromString(sort[1]);

    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortName));
    return employeeService.getEmployees(pageable);
  }

  @PostMapping("/{id}/skills")
  public SkillDto createOrUpdateSkill(
      @RequestBody SkillRequestDto skillRequestPayload,
      @PathVariable long id) {
    if (skillRequestPayload.getId() > 0) {
      return skillService.update(skillRequestPayload, id);
    } else {
      return skillService.save(skillRequestPayload, id);
    }
  }

  @GetMapping("/{id}/skills/{skillId}")
  public SkillDto getSkill(@PathVariable long id,@PathVariable long skillId) {
    return skillService.getSkillByEmployeeIdById(id,skillId);
  }

  @DeleteMapping("/{id}/skills/{skillId}")
  public String deleteSkill(@PathVariable long id,@PathVariable long skillId) {
    skillService.deleteByEmployeeIdBySkillId(id,skillId);
    return "Skill successfully deleted id: " + skillId;
  }



  @GetMapping("/{id}/skills")
  public Page<SkillDto> getSkills(@PathVariable long id,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "id,desc") String[] sort) {

    String sortName = sort[0];
    Sort.Direction direction = Sort.Direction.fromString(sort[1]);

    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortName));
    return skillService.getSkills(pageable);
  }


}
