package com.java.master.finals.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee extends AuditModel {

	private static final long serialVersionUID = -8299565093673499162L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String firstName;

	String lastName;

	@OneToMany
	Set<Skill> skills;
	
	public Employee() { }

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}
