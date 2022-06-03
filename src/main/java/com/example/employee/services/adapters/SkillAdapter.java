package com.example.employee.services.adapters;

import com.example.employee.domain.dtos.Skill;
import com.example.employee.domain.dtos.requests.SkillRequest;
import com.example.employee.domain.entities.SkillEntity;
import com.example.employee.util.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class SkillAdapter {

    public SkillEntity convert(SkillRequest skillRequest) {

        final SkillEntity skillEntity = new SkillEntity();

        skillEntity.setDescription(skillRequest.getDescription());
        skillEntity.setDuration(skillRequest.getDuration());
        skillEntity.setLastUsed(DateUtils.parse(skillRequest.getLastUsed()));

        return skillEntity;
    }

    public Skill convert(SkillEntity skillEntity) {

        final Skill skill = new Skill();

        skill.setId(skillEntity.getId());
        skill.setDescription(skillEntity.getDescription());
        skill.setDuration(skillEntity.getDuration());
        skill.setLastUsed(DateUtils.format(skillEntity.getLastUsed()));
        skill.setEmployeeId(skillEntity.getEmployeeEntity().getId());
        skill.setCreatedDate(DateUtils.format(skillEntity.getCreatedDate()));
        skill.setLastModifiedDate(DateUtils.format(skillEntity.getLastModifiedDate()));

        return skill;
    }

}
