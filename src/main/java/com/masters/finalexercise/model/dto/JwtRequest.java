package com.masters.finalexercise.model.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    private String username;

    private String password;

}
