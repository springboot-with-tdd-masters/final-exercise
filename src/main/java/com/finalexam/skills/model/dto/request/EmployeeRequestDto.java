package com.finalexam.skills.model.dto.request;

import java.util.Objects;

public class EmployeeRequestDto {

  private long id;
  private String firstname;
  private String lastname;

  public EmployeeRequestDto() {
  }

  public EmployeeRequestDto(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public EmployeeRequestDto(long id, String firstname, String lastname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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
    EmployeeRequestDto that = (EmployeeRequestDto) o;
    return id == that.id && Objects.equals(firstname, that.firstname)
        && Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, lastname);
  }
}
