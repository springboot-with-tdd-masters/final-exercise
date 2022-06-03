package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EmployeeRequest {

    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
