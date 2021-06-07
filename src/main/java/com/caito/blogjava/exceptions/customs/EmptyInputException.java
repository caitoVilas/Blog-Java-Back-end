package com.caito.blogjava.exceptions.customs;

public class EmptyInputException extends RuntimeException{
    public EmptyInputException(String stringError){
        super(stringError);
    }
}
