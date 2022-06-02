package com.softvision.masters.tdd.employeeskilltracker.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;

    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Skill> skills;

    public Employee() {}

    public Employee(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Employee(String firstname, String lastname, List<Skill> skills) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.skills = skills;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
