package com.finalexam.skills.model.dto.response;

import java.time.LocalDate;
import java.util.Objects;

public class SkillDto extends BaseDto {

  private long id;
  private String description;
  private int duration;
  private LocalDate lastUsed;
  private EmployeeDto employee;

  public SkillDto() {
  }

  public SkillDto(LocalDate createdAt, LocalDate updatedAt, String description, int duration,
      LocalDate lastUsed, EmployeeDto employee) {
    super(createdAt, updatedAt);
    this.description = description;
    this.duration = duration;
    this.lastUsed = lastUsed;
    this.employee = employee;
  }

  public SkillDto(LocalDate createdAt, LocalDate updatedAt, long id, String description,
      int duration, LocalDate lastUsed, EmployeeDto employee) {
    super(createdAt, updatedAt);
    this.id = id;
    this.description = description;
    this.duration = duration;
    this.lastUsed = lastUsed;
    this.employee = employee;
  }

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

  public EmployeeDto getEmployee() {
    return employee;
  }

  public void setEmployee(EmployeeDto employee) {
    this.employee = employee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SkillDto skillDto = (SkillDto) o;
    return id == skillDto.id && duration == skillDto.duration && Objects.equals(description,
        skillDto.description) && Objects.equals(lastUsed, skillDto.lastUsed)
        && Objects.equals(employee, skillDto.employee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, duration, lastUsed, employee);
  }
}
