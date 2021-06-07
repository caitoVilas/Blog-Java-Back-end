package com.caito.blogjava.exceptions.customs;

public class Forbidden extends RuntimeException{
    public Forbidden(String stringError){
        super(stringError);
    }
}
