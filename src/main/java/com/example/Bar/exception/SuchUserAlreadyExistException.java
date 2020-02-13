package com.example.Bar.exception;

public class SuchUserAlreadyExistException extends Exception{

    public SuchUserAlreadyExistException() {}

    public SuchUserAlreadyExistException(String message) {
        super(message);
    }
}
