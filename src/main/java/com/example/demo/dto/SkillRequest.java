package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SkillRequest {
    @NotEmpty
    private String description;

    @NotNull
    private Integer duration;

    @NotEmpty
    private String lastUsed;
}
