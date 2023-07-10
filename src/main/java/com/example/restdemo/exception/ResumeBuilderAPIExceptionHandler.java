package com.example.restdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResumeBuilderAPIExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException){

        ResumeBuilderAPIException resumeBuilderAPIException = new ResumeBuilderAPIException(
                "400",
                badRequestException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resumeBuilderAPIException);

    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> handleBadRequestException(UnauthorizedException unauthorizedException){

        ResumeBuilderAPIException resumeBuilderAPIException = new ResumeBuilderAPIException(
                "401",
                unauthorizedException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resumeBuilderAPIException);

    }

    @ExceptionHandler(value = TemplateNotFoundException.class)
    public ResponseEntity<Object> handleTemplateNotFoundException(TemplateNotFoundException templateNotFoundException){

        ResumeBuilderAPIException resumeBuilderAPIException = new ResumeBuilderAPIException(
                "404",
                templateNotFoundException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resumeBuilderAPIException);

    }

    @ExceptionHandler(value = InternalServerErrorException.class)
    public ResponseEntity<Object> handleBadRequestException(InternalServerErrorException internalServerErrorException){

        ResumeBuilderAPIException resumeBuilderAPIException = new ResumeBuilderAPIException(
                "500",
                internalServerErrorException.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resumeBuilderAPIException);

    }
}
