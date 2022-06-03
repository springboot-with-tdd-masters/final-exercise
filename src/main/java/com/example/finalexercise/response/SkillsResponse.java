package com.example.finalexercise.response;

import com.example.finalexercise.exception.SkillsTrackerAppException;
import com.example.finalexercise.exception.SkillsTrackerAppExceptionCode;
import com.example.finalexercise.model.Skills;

public class SkillsResponse {
	
	private String createdAt;
	private String updatedAt;
	private Long id;
	private String description;
	private Integer duration;
	private String lastUsed;
	private Long employeeId;
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public String getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(String lastUsed) {
		this.lastUsed = lastUsed;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public static SkillsResponse convertSkillsToResponse(Skills skillsEntity) {
		try {
			SkillsResponse response = new SkillsResponse();
			response.setCreatedAt(String.valueOf(skillsEntity.getCreatedDate()));
			response.setUpdatedAt(String.valueOf(skillsEntity.getLastModifiedDate()));
			response.setId(skillsEntity.getId());
			response.setDescription(skillsEntity.getDescription());
			response.setDuration(skillsEntity.getDuration());
			response.setLastUsed(String.valueOf(skillsEntity.getLastUsed()));
			response.setEmployeeId(skillsEntity.getEmployees().getId());
			
			return response;
		} catch (Exception e) {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.UNABLE_TO_MAP_EXCEPTION);
		}
	}

}
