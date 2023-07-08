package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResumeBuilderAPIExceptionHandler {

    @ExceptionHandler(value = {TemplateNotFoundException.class})
    public ResponseEntity<Object> handleTemplateNotFoundException(TemplateNotFoundException templateNotFoundException){

        ResumeBuilderAPIException resumeBuilderAPIException = new ResumeBuilderAPIException(
                templateNotFoundException.getMessage(),
                templateNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(resumeBuilderAPIException, HttpStatus.NOT_FOUND);
    }

}
