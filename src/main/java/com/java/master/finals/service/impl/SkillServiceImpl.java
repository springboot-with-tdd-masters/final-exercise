package com.java.master.finals.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.master.finals.exception.RecordNotFoundException;
import com.java.master.finals.model.Employee;
import com.java.master.finals.model.Skill;
import com.java.master.finals.repository.EmployeeRepository;
import com.java.master.finals.repository.SkillRepository;
import com.java.master.finals.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private SkillRepository skillRepo;

	@Override
	public Skill createUpdate(Long employeeId, Long skillId, Skill skillRequest) {

		Optional<Employee> employee = employeeRepo.findById(employeeId);

		if (employee.isPresent()) {
			Optional<Skill> skill = skillRepo.findById(skillId);

			if (skill.isPresent()) {
				Skill skillUpdate = skill.get();
				skillUpdate.setDescription(skillRequest.getDescription());
				skillUpdate.setDuration(skillRequest.getDuration());
				skillUpdate.setLastUsed(skillRequest.getLastUsed());
				return skillRepo.save(skillUpdate);
			} else {
				skillRequest.setEmployee(employee.get());
				return skillRepo.save(skillRequest);
			}
		} else {
			throw new RecordNotFoundException("Record Not Found!");
		}

	}

	@Override
	public void delete(Long employeeId, Long skillId) {
		Optional<Employee> employee = employeeRepo.findById(employeeId);

		if (employee.isPresent()) {
			Optional<Skill> skill = skillRepo.findById(skillId);

			if (skill.isPresent()) {
				skillRepo.delete(skill.get());
			} else {
				throw new RecordNotFoundException("Record Not Found!");
			}
		} else {
			throw new RecordNotFoundException("Record Not Found!");
		}
	}

	@Override
	public Page<Skill> getAll(long employeeId, Pageable pageRequest) {
		return skillRepo.findAllByEmployeeId(employeeId, pageRequest);
	}

}
