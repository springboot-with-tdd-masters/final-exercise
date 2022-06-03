package com.example.finalexercise.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.finalexercise.exception.SkillsTrackerAppException;
import com.example.finalexercise.exception.SkillsTrackerAppExceptionCode;
import com.example.finalexercise.model.Employees;
import com.example.finalexercise.model.Skills;
import com.example.finalexercise.repository.EmployeesRepository;
import com.example.finalexercise.repository.SkillsRepository;
import com.example.finalexercise.request.EmployeesRequest;
import com.example.finalexercise.request.SkillsRequest;
import com.example.finalexercise.response.EmployeesResponse;
import com.example.finalexercise.response.SkillsResponse;
import com.example.finalexercise.service.SkillsTrackerService;

@Service
public class SkillsTrackerServiceImpl implements SkillsTrackerService{
	
	@Autowired
	EmployeesRepository employeeRepository;
	
	@Autowired
	SkillsRepository skillsRepository;
	
	@Override
	public EmployeesResponse createOrUpdateEmployee(EmployeesRequest employeesRequest) {
		Employees newEmployee = new Employees();
		
		if (employeesRequest.getId() != null) {
			Optional<Employees> employeeEntity = employeeRepository.findById(employeesRequest.getId());
			if (employeeEntity.isPresent()) {
				newEmployee = employeeEntity.get();
				newEmployee.setFirstName(employeesRequest.getFirstName());
				newEmployee.setLastName(employeesRequest.getLastName());
				employeeRepository.save(newEmployee);
				
				return EmployeesResponse.convertToEmployeesResponse(newEmployee);
			} else {
				newEmployee.setFirstName(employeesRequest.getFirstName());
				newEmployee.setLastName(employeesRequest.getLastName());
				employeeRepository.save(newEmployee);
				return EmployeesResponse.convertToEmployeesResponse(newEmployee);
			}
		} else {
			newEmployee.setFirstName(employeesRequest.getFirstName());
			newEmployee.setLastName(employeesRequest.getLastName());
			employeeRepository.save(newEmployee);
			
			return EmployeesResponse.convertToEmployeesResponse(newEmployee);
		}
	}
	
	@Override
	public Page<EmployeesResponse> getAllEmployees(Pageable page) {
		return employeeRepository.findAll(page).map(EmployeesResponse::convertToEmployeesResponse);
	}

	@Override
	public EmployeesResponse getEmployeeById(Long id) {
		Optional<Employees> employeeEntity = employeeRepository.findById(id);
		
		if (employeeEntity.isPresent()) {
			return EmployeesResponse.convertToEmployeesResponse(employeeEntity.get());
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public SkillsResponse addSkill(Long id, SkillsRequest skillsRequest) {
		Optional<Employees> employee = employeeRepository.findById(id);
		
		if (employee.isPresent()) {
			Skills skills = new Skills();
			skills.setDescription(skillsRequest.getDescription());
			skills.setDuration(validateDuration(skillsRequest));
			skills.setLastUsed(validateDate(skillsRequest));
			skills.setEmployees(employee.get());
			skillsRepository.save(skills);
			
			return SkillsResponse.convertSkillsToResponse(skills);
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public SkillsResponse updateSkill(Long employeeId, Long skillId, SkillsRequest skillsRequest) {
		Optional<Employees> employees = employeeRepository.findById(employeeId);
		
		if (employees.isPresent()) {
			Optional<Skills> skillsEntity= Optional.ofNullable(skillsRepository.findByEmployeesIdAndId(employeeId, skillId));
			if (skillsEntity.isPresent()) {
				Skills newSkill = skillsEntity.get();
				newSkill.setDescription(skillsRequest.getDescription());
				newSkill.setDuration(validateDuration(skillsRequest));
				newSkill.setLastUsed(validateDate(skillsRequest));
				
				skillsRepository.save(newSkill);
				return SkillsResponse.convertSkillsToResponse(newSkill);
			} else {
				throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.SKILL_NOT_FOUND_EXCEPTION);
			}
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public Page<SkillsResponse> getSkillsByEmployeeId(Long id, Pageable page) {
		Optional<Employees> employees = employeeRepository.findById(id);
		
		if (employees.isPresent()) {
			return skillsRepository.findByEmployeesId(id, page).map(SkillsResponse::convertSkillsToResponse);
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}
	
	@Override
	public Page<SkillsResponse> getSkill(Long employeeId, Long skillsId, Pageable page) {
		Optional<Page<Skills>> skill = Optional.ofNullable(skillsRepository.findByEmployeesIdAndId(employeeId, skillsId, page));
		
		if (skill.isPresent()) {
			return skillsRepository.findByEmployeesIdAndId(employeeId, skillsId, page).map(SkillsResponse::convertSkillsToResponse);
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.RECORD_NOT_FOUND);
		}
	}

	@Override
	public Employees deleteEmployee(Long id) {
		Optional<Employees> employee = employeeRepository.findById(id);
		
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
			return employee.get();
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public Skills deleteSkill(Long employeeId, Long skillsId) {
		Optional<Employees> employee = employeeRepository.findById(employeeId);
		Optional<Skills> skills = skillsRepository.findById(skillsId);
		
		if (employee.isPresent()) {
			if (skills.isPresent()) {
				skillsRepository.deleteById(skillsId);
				return skills.get();
			} else {
				throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.SKILL_NOT_FOUND_EXCEPTION);
			}
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.EMPLOYEE_NOT_FOUND_EXCEPTION);
		}
	}
	
	private Integer validateDuration(SkillsRequest skillsRequest) {
		if (skillsRequest.getDuration() != null && skillsRequest.getDuration() > 0) {
			return skillsRequest.getDuration();
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.INVALID_DURATION_EXCEPTION);
		}
	}
	
	private LocalDate validateDate(SkillsRequest skillsRequest) {
		if (skillsRequest.getLastUsed() != null && !skillsRequest.getLastUsed().trim().equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			try {
				Date duration = sdf.parse(skillsRequest.getLastUsed());
				return duration.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			} catch (Exception e) {
				throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.INVALID_DATE_EXCEPTION);
			}
		} else {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.INVALID_DATE_EXCEPTION);
		}
	}

}
