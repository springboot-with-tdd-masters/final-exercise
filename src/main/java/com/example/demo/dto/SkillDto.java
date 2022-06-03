package com.example.demo.dto;

import com.example.demo.model.Skill;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SkillDto {
    private Date createdAt;
    private Date updatedAt;
    private Long id;
    private String description;
    private Integer duration;
    private String lastUsed;
    private Long employee_Id;

    public static SkillDto convertToDto(Skill skill) {
        SkillDto skillDto = new SkillDto();
        skillDto.setCreatedAt(skill.getCreatedAt());
        skillDto.setUpdatedAt(skill.getUpdatedAt());
        skillDto.setId(skill.getId());
        skillDto.setDescription(skill.getDescription());
        skillDto.setDuration(skill.getDuration());
        skillDto.setLastUsed(skill.getLastUsed().toString());
        skillDto.setEmployee_Id(skill.getEmployee().getId());
        return skillDto;
    }
}
