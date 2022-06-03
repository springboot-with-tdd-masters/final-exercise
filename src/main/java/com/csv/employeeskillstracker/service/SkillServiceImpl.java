package com.csv.employeeskillstracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.csv.employeeskillstracker.exception.RecordNotFoundException;
import com.csv.employeeskillstracker.model.Employee;
import com.csv.employeeskillstracker.model.Skill;
import com.csv.employeeskillstracker.repository.EmployeeRepository;
import com.csv.employeeskillstracker.repository.SkillRepository;

@Service
public class SkillServiceImpl implements SkillService{

	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public Skill createUpdate(Long employeeId, Long skillId, Skill skillEntity) throws RecordNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		employee.orElseThrow(RecordNotFoundException::new);
		
		Optional<Skill> skill = skillRepository.findById(skillId);
		
		if(skill.isPresent()) {
			Skill newSkill = skill.get();
			newSkill.setDescription(skillEntity.getDescription());
			newSkill.setDuration(skillEntity.getDuration());
			newSkill.setLastUsed(skillEntity.getLastUsed());
			return skillRepository.save(newSkill);
		} else {
			skillEntity.setEmployee(employee.get());
			return skillRepository.save(skillEntity);
		}
	}

	@Override
	public void deleteSkill(Long employeeId, Long skillId) throws RecordNotFoundException {
		employeeRepository.findById(employeeId).orElseThrow(RecordNotFoundException::new);
		skillRepository.findById(skillId).orElseThrow(RecordNotFoundException::new);
		skillRepository.deleteById(skillId);
		
	}

	@Override
	public Page<Skill> getAllSkills(Pageable pageable, Long employeeId) {
		return skillRepository.findAllSkillsByEmployeeId(pageable, employeeId);
	}

}
