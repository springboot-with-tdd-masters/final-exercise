package com.example.finalexercise.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.finalexercise.exception.SkillNotFoundException;
import com.example.finalexercise.model.Employee;
import com.example.finalexercise.model.Skill;
import com.example.finalexercise.model.dto.SkillDto;
import com.example.finalexercise.model.dto.SkillRequest;
import com.example.finalexercise.repository.SkillRepository;
import com.example.finalexercise.util.DateUtil;

@Transactional
@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public SkillDto addSkill(SkillRequest request, Long employeeId) {
		Employee employee = employeeService.findEmployee(employeeId);

		Skill newSkill = new Skill();
		newSkill.setDescription(request.getDescription());
		newSkill.setDuration(request.getDuration());
		newSkill.setLastUsed(DateUtil.parse(request.getLastUsed()));
		newSkill.setEmployee(employee);
		
		employee.addSkill(newSkill);
		
		Skill savedSkill = skillRepository.save(newSkill);
		
		return SkillDto.convertToDto(savedSkill);
	}

	@Override
	public SkillDto updateSkill(Long skillId, Long employeeId, SkillRequest request) {
		Employee emp = employeeService.findEmployee(employeeId);
	
		Skill skill = skillRepository.findById(skillId).orElseThrow(SkillNotFoundException::new);
		skill.setDescription(request.getDescription());
		skill.setDuration(request.getDuration());
		skill.setLastUsed(DateUtil.parse(request.getLastUsed()));
		
		return SkillDto.convertToDto(skill);
	}

	@Override
	public Page<SkillDto> getSkills(Long employeeId, Pageable pageable) {
		return skillRepository.findByEmployeeId(employeeId, pageable).map(SkillDto::convertToDto);
	}

	@Override
	public void deleteSkill(Long skillId, Long empId) {
		Skill skill = findSkill(skillId, empId);
		skillRepository.delete(skill);
	}
	
	private Skill findSkill(Long id, Long employeeId) {
		employeeService.findEmployee(employeeId); //checks employee if existing
		return skillRepository.findById(id).orElseThrow(SkillNotFoundException::new);
	}

}
