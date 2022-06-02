package com.example.employee.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class SkillEntity extends AbstractDomain {

    private String description;

    private int duration;

    private Date lastUsed;

    @ManyToOne
    private EmployeeEntity employeeEntity;

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

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillEntity that = (SkillEntity) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(employeeEntity, that.employeeEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, employeeEntity);
    }
}
