package com.example.restdemo.create;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GenerateJSON {
    public void generateJSON(Map<String, Object> resumeJSON) throws IOException {

        HashMap<String, Object> newJson = new HashMap<>();

        String[] prsnlInfo = String.valueOf(resumeJSON.get("personal_information")).split(",");


        String Name = prsnlInfo[0].split("=")[1];
        String LastName = prsnlInfo[1].split("=")[1];
        String EmailAddress = prsnlInfo[2].split("=")[1];
        String PhoneNumber = prsnlInfo[3].split("=")[1];
        String LinkedIn = prsnlInfo[4].split("=")[1];
        LinkedIn = LinkedIn.substring(0, LinkedIn.length() - 1);

        newJson.put("Name", Name);
        newJson.put("LastName", LastName);
        newJson.put("EmailAddress", EmailAddress);
        newJson.put("PhoneNumber", PhoneNumber);
        newJson.put("LinkedIn", LinkedIn);
        newJson.put("job_title", resumeJSON.get("job_title"));
        newJson.put("skills", resumeJSON.get("skills"));
        newJson.put("career_objective", resumeJSON.get("career_objective"));
        newJson.put("experience", resumeJSON.get("experience"));
        newJson.put("education", resumeJSON.get("education"));
        newJson.put("achievements", resumeJSON.get("achievements"));


        ObjectMapper mapper = new ObjectMapper();
        //String json = mapper.writeValueAsString(newJson);

        String jsonPath = "./rest-demo/src/main/resources/generated/templateJSON.json";
        Files.deleteIfExists(Paths.get(jsonPath));
        mapper.writeValue(new File(jsonPath), newJson);
    }
}
