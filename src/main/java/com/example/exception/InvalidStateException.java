package com.example.exception;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(String msg) {
        super(msg);
    }
}
