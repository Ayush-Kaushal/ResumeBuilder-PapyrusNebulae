package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;


public class ResumeBuilderAPIException {
    private final String message;
    private final Throwable throwable;
    private final org.springframework.http.HttpStatus httpStatus;

    public ResumeBuilderAPIException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }


    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
