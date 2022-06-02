package com.masters.masters.exercise.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SkillsDto {

    private String description;

    private int duration;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate lastUsed;

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
}
