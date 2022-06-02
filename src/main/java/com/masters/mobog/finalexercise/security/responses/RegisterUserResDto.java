package com.masters.mobog.finalexercise.security.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserResDto {
    private String username;
    private String userId;
}
