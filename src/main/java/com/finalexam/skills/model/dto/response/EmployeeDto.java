package com.finalexam.skills.model.dto.response;

import com.finalexam.skills.model.entity.Employee;
import java.time.LocalDate;
import java.util.Objects;

public class EmployeeDto extends BaseDto {

  private Long id;
  private String firstname;
  private String lastname;

  public EmployeeDto(long id, String firstname, String lastname, LocalDate createdAt,
      LocalDate updatedAt) {
    super(createdAt, updatedAt);
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public EmployeeDto() {

  }

  public EmployeeDto(Employee employee) {
    this(employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getCreatedAt(),
        employee.getUpdatedAt());
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EmployeeDto that = (EmployeeDto) o;
    return Objects.equals(id, that.id) && Objects.equals(firstname,
        that.firstname) && Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
  }


}
