package com.example.finalexercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkillRequest {

	private String description;
	private Integer duration;
	private String lastUsed;
	
	public static SkillRequest request(String description, Integer duration, String lastUsed) {
		return new SkillRequest(description, duration, lastUsed);
	}
	
}
