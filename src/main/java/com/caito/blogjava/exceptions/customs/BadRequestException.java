package com.caito.blogjava.exceptions.customs;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String stringError){
        super(stringError);
    }
}
