package com.advancejava.finalexercise.service;

import com.advancejava.finalexercise.model.Skill;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SkillService {
    Skill addSkill(Skill skill, Long id);

    Skill getSkill(Long id);

    List<Skill> getAllSkills();

    Skill updateSkill(Skill request,Long empId, Long id);

    String deleteSkill(Long id);

    Page<Skill> getWithPaginationAndSort(int page, int limit, String field, String order);
}
