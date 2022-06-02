package com.masters.finalexercise.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private final String jwttoken;

}
