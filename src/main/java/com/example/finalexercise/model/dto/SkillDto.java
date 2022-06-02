package com.example.finalexercise.model.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import com.example.finalexercise.model.Skill;
import com.example.finalexercise.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDto {

	private Date createdDate;
	private Date updatedDate;
	private Long id;
	private String description;
	private Integer duration;
	private String lastUsed;
	private Long employeeId;
	
	public static SkillDto convertToDto(Skill skill) {
		return new SkillDto(skill.getCreatedDate(), skill.getUpdatedDate(), skill.getId(), 
				skill.getDescription(), skill.getDuration(), DateUtil.format(skill.getLastUsed()), 
				skill.getEmployee().getId());
	}	
}
