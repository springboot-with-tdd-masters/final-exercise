package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Skill;
import org.springframework.stereotype.Service;

@Service
public class SkillAdapter {

    public Skill mapToSkill(EmployeeSkillRequest skillRequest){
        try {
            Skill skill = new Skill();
            skill.setDuration(skillRequest.getDuration());
            skill.setDescription(skillRequest.getDescription());
            skill.setLastUsed(skillRequest.getLastUsed());
            return skill;
        } catch (Exception e){
            throw e;
        }
    }
}
