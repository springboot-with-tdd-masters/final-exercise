package com.example.employee.services.impl;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.domain.entities.EmployeeEntity;
import com.example.employee.domain.entities.SkillEntity;
import com.example.employee.exceptions.DefaultException;
import com.example.employee.exceptions.codes.EmployeeExceptionCode;
import com.example.employee.exceptions.codes.SkillExceptionCode;
import com.example.employee.repositories.EmployeeRepository;
import com.example.employee.repositories.SkillRepository;
import com.example.employee.services.SkillService;
import com.example.employee.services.adapters.SkillAdapter;
import com.example.employee.util.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    private final EmployeeRepository employeeRepository;

    private final SkillRepository skillRepository;

    private final SkillAdapter skillAdapter;

    public SkillServiceImpl(
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository,
            SkillAdapter skillAdapter) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.skillAdapter = skillAdapter;
    }

    @Override
    public Page<Skill> findAllSkillsOf(Long employeeId, Pageable pageRequest) {

        validateEmployeeEntityExists(employeeId);

        final Page<SkillEntity> skillEntityPage = skillRepository.findAllByEmployeeEntityId(employeeId, pageRequest);

        return skillEntityPage.map(skillAdapter::convert);
    }

    @Override
    public Skill findSkillOf(Long employeeId, Long id) {

        validateEmployeeEntityExists(employeeId);

        final SkillEntity skillEntity = findEntityExits(employeeId, id);

        return skillAdapter.convert(skillEntity);
    }

    @Override
    public Skill createSkillOf(Long employeeId, SkillRequest skillRequest) {

        final EmployeeEntity employeeEntity = validateEmployeeEntityExists(employeeId);

        final SkillEntity skillEntity = skillAdapter.convert(skillRequest);
        skillEntity.setEmployeeEntity(employeeEntity);

        skillRepository.save(skillEntity);

        return skillAdapter.convert(skillEntity);
    }

    @Override
    public Skill updateSkillOf(Long employeeId, Long id, SkillRequest skillRequest) {

        validateEmployeeEntityExists(employeeId);

        final SkillEntity skillEntity = findEntityExits(employeeId, id);
        skillEntity.setDescription(skillRequest.getDescription());
        skillEntity.setDuration(skillRequest.getDuration());
        skillEntity.setLastUsed(DateUtils.parse(skillRequest.getLastUsed()));

        skillRepository.save(skillEntity);

        return skillAdapter.convert(skillEntity);
    }

    @Override
    public void deleteSkillOf(Long employeeId, Long id) {

        validateEmployeeEntityExists(employeeId);

        final SkillEntity skillEntity = findEntityExits(employeeId, id);

        skillRepository.delete(skillEntity);
    }

    private SkillEntity findEntityExits(Long employeeId, Long id) {
        return skillRepository.findByEmployeeEntityIdAndId(employeeId, id)
                .orElseThrow(() -> new DefaultException(SkillExceptionCode.SKILL_NOT_FOUND));
    }

    private EmployeeEntity validateEmployeeEntityExists(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DefaultException(EmployeeExceptionCode.EMPLOYEE_NOT_FOUND));
    }
}
