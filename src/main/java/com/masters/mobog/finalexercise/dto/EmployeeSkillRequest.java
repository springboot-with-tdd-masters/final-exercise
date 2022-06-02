package com.masters.mobog.finalexercise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeSkillRequest {
    private String description;
    private Integer duration;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate lastUsed;
}
