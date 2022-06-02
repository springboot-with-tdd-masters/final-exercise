package com.masters.mobog.finalexercise.exceptions;

import org.springframework.http.HttpStatus;

public class FinalExerciseException extends RuntimeException{
    FinalExerciseExceptionsCode code;

    public FinalExerciseException(FinalExerciseExceptionsCode code){
        super(code.getMessage());
        this.code = code;
    }
    public HttpStatus getStatus(){
        return code.getStatus();
    }
}
