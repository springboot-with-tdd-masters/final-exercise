package com.masters.mobog.finalexercise.security.requests;

import lombok.Data;

@Data
public class RegisterUserReqDto {
    private String username;
    private String password;
}
