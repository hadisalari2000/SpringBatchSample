package com.examples.SpringBatchSample.handler.exception;

import com.examples.SpringBatchSample.utils.ApplicationProperties;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class GlobalException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public GlobalException() {
        super();
    }

    private GlobalException(String message,HttpStatus status) {
        super(message);
        this.status=status;
    }

    public static GlobalException getInstance(String key, String... args){
        String message= String.format(ApplicationProperties.getProperty(key), (Object) args);
        return new GlobalException(message,BAD_REQUEST);
    }

}