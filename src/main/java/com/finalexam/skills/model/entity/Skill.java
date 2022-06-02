package com.finalexam.skills.model.entity;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Skill extends AuditModel{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;

  private Integer duration;

  @ManyToOne
  private Employee employee;

  private LocalDate lastUsed;


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

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public LocalDate getLastUsed() {
    return lastUsed;
  }

  public void setLastUsed(LocalDate lastUsed) {
    this.lastUsed = lastUsed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Skill skill = (Skill) o;
    return Objects.equals(id, skill.id) && Objects.equals(description,
        skill.description) && Objects.equals(duration, skill.duration)
        && Objects.equals(employee, skill.employee) && Objects.equals(lastUsed,
        skill.lastUsed);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, description, duration, employee, lastUsed);
  }
}
