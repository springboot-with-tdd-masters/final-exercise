package com.finalexam.skills.model.entity;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee extends AuditModel{

  private static final long serialVersionUID = -2338626292552177485L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstname;
  private String lastname;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Set<Skill> skills;

  public Employee() {
  }

  public Employee(String firstName, String lastName) {
    super();
    this.firstname = firstName;
    this.lastname = lastName;
  }

  public Employee(Long id, String firstname, String lastname) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
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

  public Set<Skill> getSkills() {
    return skills;
  }

  public void setSkills(Set<Skill> skills) {
    this.skills = skills;
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
    Employee employee = (Employee) o;
    return Objects.equals(id, employee.id) && Objects.equals(firstname,
        employee.firstname) && Objects.equals(lastname, employee.lastname)
        && Objects.equals(skills, employee.skills);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, firstname, lastname, skills);
  }
}
