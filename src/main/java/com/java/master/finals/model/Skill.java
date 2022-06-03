package com.java.master.finals.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class Skill extends AuditModel {
	private static final long serialVersionUID = -1826473238059038910L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String description;
	
	int duration;
	
	LocalDate lastUsed;
	
	@ManyToOne
	@JsonIdentityReference(alwaysAsId= true)
	@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonProperty("employee_id")
	Employee employee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public LocalDate getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(LocalDate lastUsed) {
		this.lastUsed = lastUsed;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
