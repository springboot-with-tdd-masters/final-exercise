package com.example.demo.service;

import com.example.demo.dto.SkillDto;
import com.example.demo.dto.SkillRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    public SkillDto createNewSkill(Long employeeId, SkillRequest skillRequest) {
        Optional<Employee> retrievedEmployee = findEmployeeById(employeeId);
        if (retrievedEmployee.isEmpty()) {
            throw new NotFoundException(String.format("Employee with id %s not found", employeeId));
        }
        return buildSkill(retrievedEmployee.get(), skillRequest);
    }

    private SkillDto buildSkill(Employee employee, SkillRequest skillRequest) {
        Skill savedSkill = skillRepository.save(Skill.builder().description(skillRequest.getDescription()).duration(skillRequest.getDuration())
                .lastUsed(LocalDate.parse(skillRequest.getLastUsed())).employee(employee).build());
        return SkillDto.convertToDto(savedSkill);
    }

    public SkillDto readSkillById(Long id) {
        Optional<Skill> retrievedSkill = skillRepository.findById(id);
        if (retrievedSkill.isEmpty()) {
            throw new NotFoundException(String.format("Skill with id %s not found", id));
        }
        return SkillDto.convertToDto(retrievedSkill.get());
    }

    public Page<SkillDto> readAllSkills(Long employeeId, Pageable pageable) {
        Page<Skill> skillPage = skillRepository.findByEmployeeId(employeeId, pageable);
        Page<SkillDto> dtoPage = skillPage.map(new Function<Skill, SkillDto>() {
            @Override
            public SkillDto apply(Skill skill) {
                return SkillDto.convertToDto(skill);
            }
        });
        return dtoPage;
    }

    @Transactional
    public SkillDto updateSkill(Long employeeId, Long skillId, SkillRequest skillRequest) {
        List<Skill> skillByEmployeeId = skillRepository.findByEmployeeId(employeeId);
        if (skillByEmployeeId.isEmpty()) {
            throw new NotFoundException(String.format("Skill ID %s of the given Employee is not found", employeeId));
        }

        Skill oldSkill = skillByEmployeeId.get(0);
        oldSkill.setDescription(skillRequest.getDescription());
        oldSkill.setDuration(skillRequest.getDuration());
        oldSkill.setLastUsed(LocalDate.parse(skillRequest.getLastUsed()));
        Skill savedSkill = skillRepository.save(oldSkill);
        return SkillDto.convertToDto(savedSkill);
    }


    public void deleteSkillById(Long employeeId, Long skillId) {
        List<Skill> skillByEmployeeId = skillRepository.findByEmployeeId(employeeId);
        if (skillByEmployeeId.isEmpty()) {
            throw new NotFoundException(String.format("Skill ID %s of the given Employee is not found", employeeId));
        }
        skillRepository.deleteById(skillId);
    }

    private Optional<Employee> findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

}
