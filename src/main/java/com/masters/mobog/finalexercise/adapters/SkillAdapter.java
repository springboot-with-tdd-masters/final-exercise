package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.AddEmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Skill;

public class SkillAdapter {

    public Skill mapToSkill(AddEmployeeSkillRequest skillRequest){
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
