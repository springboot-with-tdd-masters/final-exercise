package com.masters.mobog.finalexercise.adapters;

import com.masters.mobog.finalexercise.dto.EmployeeSkillRequest;
import com.masters.mobog.finalexercise.entities.Skill;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseException;
import com.masters.mobog.finalexercise.exceptions.FinalExerciseExceptionsCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillAdapter {

    public Skill mapToSkill(EmployeeSkillRequest skillRequest){
        boolean isValid = Optional.ofNullable(skillRequest.getDescription()).isPresent()
                && Optional.ofNullable(skillRequest.getLastUsed()).isPresent()
                && Optional.ofNullable(skillRequest.getDuration()).isPresent();
        if(isValid){

            Skill skill = new Skill();
            skill.setDuration(skillRequest.getDuration());
            skill.setDescription(skillRequest.getDescription());
            skill.setLastUsed(skillRequest.getLastUsed());
            return skill;
        } else {
            throw new FinalExerciseException(FinalExerciseExceptionsCode.MAPPING_EXCEPTION);
        }
    }
}
