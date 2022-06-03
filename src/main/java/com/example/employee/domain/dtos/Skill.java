package com.example.employee.domain.dtos;

import java.util.Objects;

public class Skill extends AbstractDto {

    private String description;

    private int duration;

    private String lastUsed;

    private Long employeeId;

    public Skill() {}

    public Skill(String description, int duration, String lastUsed) {
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

    public String getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(String lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(description, skill.description) &&
                Objects.equals(employeeId, skill.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, employeeId);
    }
}
