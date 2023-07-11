package com.example.restdemo.controller;

import java.io.IOException;
import java.util.*;
import com.example.restdemo.create.GenerateJSON;
import com.example.restdemo.create.GeneratePDF;
import com.example.restdemo.exception.BadRequestException;
import com.example.restdemo.exception.InternalServerErrorException;
import com.example.restdemo.exception.TemplateNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class ResumeAPIService {

    @PostMapping(value = "/resume")
    public ResponseEntity<Object> createResume(@RequestBody Map<String, Object> resumeJSON){
        try {

            if(!resumeJSON.containsKey("template_id")){
                throw new BadRequestException("Bad Request");
            }

            String id = String.valueOf(resumeJSON.get("template_id"));

            if (id.length() == 0) {
                throw new BadRequestException("Bad Request");
            }

            int templateId = Integer.parseInt(id);

            if (templateId != 1 && templateId != 2 && templateId != 3) {
                throw new TemplateNotFoundException("Template not found");
            }

            GenerateJSON gj = new GenerateJSON();
            gj.generateJSON(resumeJSON);

            GeneratePDF generate = new GeneratePDF();
            generate.generatePDF(templateId);

            byte[] content = Files.readAllBytes(Paths.get("./rest-demo/src/main/resources/generated/generatedResume.pdf"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        }
        catch (IOException e){
            throw new InternalServerErrorException("Internal Server Error");
        }
    }
}
