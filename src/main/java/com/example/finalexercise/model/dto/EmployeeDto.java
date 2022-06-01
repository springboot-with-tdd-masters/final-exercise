package com.example.finalexercise.model.dto;

import java.util.Date;
import java.util.Objects;

import com.example.finalexercise.model.Employee;


public class EmployeeDto {
	
	private Date createdDate;
	private Date updatedDate;
	private Long id;
	private String firstname;
	private String lastname;
	
	public EmployeeDto(Date createdDate, Date updatedDate, Long id, String firstname, String lastname) {
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public EmployeeDto(Employee employee) {
		this(employee.getCreatedDate(), employee.getUpdatedDate(), employee.getId(), 
				employee.getFirstname(), employee.getLastname());
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public static EmployeeDto convertToDto(Employee employee) {
		return new EmployeeDto(employee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdDate, firstname, id, lastname, updatedDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDto other = (EmployeeDto) obj;
		return Objects.equals(createdDate, other.createdDate) && Objects.equals(firstname, other.firstname)
				&& Objects.equals(id, other.id) && Objects.equals(lastname, other.lastname)
				&& Objects.equals(updatedDate, other.updatedDate);
	}

}
