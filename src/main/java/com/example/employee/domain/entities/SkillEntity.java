package com.example.employee.domain.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class SkillEntity extends AbstractDomain {

    private String description;

    private int duration;

    private LocalDateTime lastUsed;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EmployeeEntity employeeEntity;

    public SkillEntity() {}

    public SkillEntity(String description, int duration, LocalDateTime lastUsed) {
        this.description = description;
        this.duration = duration;
        this.lastUsed = lastUsed;
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

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
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
