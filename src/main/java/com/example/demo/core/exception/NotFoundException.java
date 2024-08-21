package com.example.demo.core.exception;


public class NotFoundException extends RuntimeException{
    public NotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
    public NotFoundException(String message){
        super(message);
    }
    public NotFoundException(){
        super();
    }
}
