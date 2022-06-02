package com.masters.finalexercise.repository;

import com.masters.finalexercise.common.repository.BaseRepository;
import com.masters.finalexercise.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends BaseRepository<Skill, Long> {

	@Async
    public Page<Skill> findAllByEmployeeId(Long employeeId, Pageable page);

	@Async
    public Optional<Skill> findByIdAndEmployeeId(Long skillId, Long employeeId);

}
