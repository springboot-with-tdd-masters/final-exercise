package com.example.finalexercise.response;

import com.example.finalexercise.exception.SkillsTrackerAppException;
import com.example.finalexercise.exception.SkillsTrackerAppExceptionCode;
import com.example.finalexercise.model.Employees;

public class EmployeesResponse {
	
	private String createdAt;
	private String updatedAt;
	private Long id;
	private String firstName;
	private String lastName;
	
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public static EmployeesResponse convertToEmployeesResponse(Employees employeesEntity) {
		try {
			EmployeesResponse response = new EmployeesResponse();
			response.setCreatedAt(String.valueOf(employeesEntity.getCreatedDate()));
			response.setUpdatedAt(String.valueOf(employeesEntity.getLastModifiedDate()));
			response.setId(employeesEntity.getId());
			response.setFirstName(employeesEntity.getFirstName());
			response.setLastName(employeesEntity.getLastName());
			
			return response;
			
		} catch (Exception e) {
			throw new SkillsTrackerAppException(SkillsTrackerAppExceptionCode.UNABLE_TO_MAP_EXCEPTION);
		}
	}
	
}
