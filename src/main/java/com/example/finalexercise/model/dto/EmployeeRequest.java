package com.example.finalexercise.model.dto;

import java.util.Objects;

public class EmployeeRequest {

	private Long id;
	private String firstname;
	private String lastname;
	
	public EmployeeRequest() {}
	
	public EmployeeRequest(Long id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public EmployeeRequest(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstname, id, lastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeRequest other = (EmployeeRequest) obj;
		return Objects.equals(firstname, other.firstname) && Objects.equals(id, other.id)
				&& Objects.equals(lastname, other.lastname);
	}
	
}
