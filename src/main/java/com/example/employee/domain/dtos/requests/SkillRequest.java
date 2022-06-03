package com.example.employee.domain.dtos.requests;

import com.example.employee.controllers.validation.annotations.DateFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SkillRequest {

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 0, message = "Duration must be positive number")
    private Integer duration;

    @NotNull(message = "Last used is required")
    @DateFormat(pattern = "yyyy-MM-dd", message = "Last used skill must have a date with format yyyy-MM-dd (i.e 2020-06-03)")
    private String lastUsed;

    public SkillRequest() {}

    private SkillRequest(String description, Integer duration, String lastUsed) {
        this.description = description;
        this.duration = duration;
        this.lastUsed = lastUsed;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getLastUsed() {
        return lastUsed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillRequest that = (SkillRequest) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(duration, that.duration) &&
                Objects.equals(lastUsed, that.lastUsed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, duration, lastUsed);
    }

    public static SkillRequest of(String description, Integer duration, String lastUsed) {
        return new SkillRequest(description, duration, lastUsed);
    }

    public static SkillRequest empty() {
        return new SkillRequest();
    }
}
