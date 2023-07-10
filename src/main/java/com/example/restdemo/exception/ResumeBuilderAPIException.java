package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;


public class ResumeBuilderAPIException {
    private final String code;
    private final String message;

    public ResumeBuilderAPIException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
