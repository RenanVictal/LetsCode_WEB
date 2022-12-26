package com.example.exception;

public class NotAllowedNameExcption extends RuntimeException{
    public NotAllowedNameExcption(String msg){
        super(msg);
    }
}
