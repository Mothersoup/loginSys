package com.example.loginsystem.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException( String message){
        super(message);



    }


}
