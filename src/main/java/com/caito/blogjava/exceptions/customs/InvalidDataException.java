package com.caito.blogjava.exceptions.customs;

import org.springframework.validation.BindingResult;

public class InvalidDataException extends RuntimeException{
    private final transient BindingResult result;

    public InvalidDataException(BindingResult result) {
        super();
        this.result = result;
    }
    public InvalidDataException(String message, BindingResult result){
        super(message);
        this.result = result;
    }
    public InvalidDataException(String message){
        super(message);
        this.result = null;
    }
    public BindingResult getResult(){
        return result;
    }
}
