package com.example.finalexercise.model.dto;

import java.util.Date;

import com.example.finalexercise.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
	
	private Date createdDate;
	private Date updatedDate;
	private Long id;
	private String firstname;
	private String lastname;
	
	public EmployeeDto(Employee employee) {
		this(employee.getCreatedDate(), employee.getUpdatedDate(), employee.getId(), 
				employee.getFirstname(), employee.getLastname());
	}

	public static EmployeeDto convertToDto(Employee employee) {
		return new EmployeeDto(employee);
	}
}
