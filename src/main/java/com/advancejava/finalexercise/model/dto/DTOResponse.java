package com.advancejava.finalexercise.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOResponse<T> {

    private int recordLimit;
    private T record;


}
