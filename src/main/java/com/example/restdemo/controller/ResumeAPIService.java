package com.example.restdemo.controller;

import java.util.*;
import com.example.restdemo.create.GenerateJSON;
import com.example.restdemo.create.GeneratePDF;
import com.example.restdemo.exception.TemplateNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class ResumeAPIService {

    @PostMapping(value = "/resume")
    public ResponseEntity<Object> createResumeDetails(@RequestBody Map<String, Object> resumeJSON) throws Exception {

        int id = Integer.parseInt(String.valueOf(resumeJSON.get("template_id")));

        if(id != 1 && id != 2 && id != 3){
            throw new TemplateNotFoundException("Template Not Found");
        }

        GenerateJSON gj = new GenerateJSON();
        gj.generateJSON(resumeJSON);

        GeneratePDF generate = new GeneratePDF();
        generate.generatePDF(id);

        byte[] content = Files.readAllBytes(Paths.get("./rest-demo/src/main/resources/generated/generatedResume.pdf"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(content, headers, HttpStatus.OK);

    }
}
