package com.example.restdemo.create;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.example.restdemo.exception.BadRequestException;
import com.example.restdemo.exception.InternalServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateJSON {
    public void generateJSON(Map<String, Object> resumeJSON) {
        try {

            HashMap<String, Object> newJson = new HashMap<>();

            String[] prsnlInfo = String.valueOf(resumeJSON.get("personal_information")).split(",");

            String Name = prsnlInfo[0].split("=")[1];
            String LastName = prsnlInfo[1].split("=")[1];
            String EmailAddress = prsnlInfo[2].split("=")[1];
            String PhoneNumber = prsnlInfo[3].split("=")[1];
            String LinkedIn = prsnlInfo[4].split("=")[1];
            LinkedIn = LinkedIn.substring(0, LinkedIn.length() - 1);


            boolean noSkills = String.valueOf(resumeJSON.get("skills")).split(",")[0].equals("[]");
            if(noSkills){
                throw new BadRequestException("Bad Request");
            }


            String edDetails = String.valueOf(resumeJSON.get("education"));
            List<HashMap<String, String>> education = getEducation(edDetails);

            String expDetails = String.valueOf(resumeJSON.get("experience"));
            List<HashMap<String, String>> experience = getExperience(expDetails);

            String achvDetails = String.valueOf(resumeJSON.get("achievements"));
            List<HashMap<String, String>> achievements = getAchievements(achvDetails);


            newJson.put("Name", Name);
            newJson.put("LastName", LastName);
            newJson.put("EmailAddress", EmailAddress);
            newJson.put("PhoneNumber", PhoneNumber);
            newJson.put("LinkedIn", LinkedIn);
            newJson.put("JobTitle", resumeJSON.get("job_title"));
            newJson.put("Skills", resumeJSON.get("skills"));
            newJson.put("Summary", resumeJSON.get("career_objective"));
            newJson.put("Experience", experience);
            newJson.put("Education", education);
            newJson.put("Achievements", achievements);


            for (Object val : newJson.values()) {
                if (val == null || String.valueOf(val).length() == 0) {
                    throw new BadRequestException("Bad Request");
                }
            }

            ObjectMapper mapper = new ObjectMapper();

            String jsonPath = "./rest-demo/src/main/resources/generated/templateJSON.json";
            Files.deleteIfExists(Paths.get(jsonPath));
            mapper.writeValue(new File(jsonPath), newJson);
        }
        catch (IOException e){
            throw new InternalServerErrorException("Internal Server Error");
        }
        catch (Exception e){
            throw new BadRequestException("Bad Request");
        }

    }

    public List<HashMap<String, String>> getEducation(String educationDetails){
        List<HashMap<String, String>> result = new ArrayList<>();

        String schoolPattern = "school_name=(.*?)[,}]";
        String yearPattern = "passing_year=(.*?)[,}]";
        String descPattern = "description=(.*?)}";

        List<String> schools = new ArrayList<>();
        List<String> passingYears = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();

        Pattern regexPattern = Pattern.compile(schoolPattern);
        Matcher matcher = regexPattern.matcher(educationDetails);

        while (matcher.find()) {
            schools.add(matcher.group(1).trim());
        }

        regexPattern = Pattern.compile(yearPattern);
        matcher = regexPattern.matcher(educationDetails);

        while (matcher.find()) {
            passingYears.add(matcher.group(1).trim());
        }

        regexPattern = Pattern.compile(descPattern);
        matcher = regexPattern.matcher(educationDetails);

        while (matcher.find()) {
            descriptions.add(matcher.group(1).trim());
        }


        if(schools.size() != passingYears.size() || schools.size() != descriptions.size()){
            throw new BadRequestException("Bad Request");
        }

        for (int i = 0; i < schools.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();

            if(schools.get(i) == null || schools.get(i).length() == 0
            || passingYears.get(i) == null || passingYears.get(i).length() == 0
            || descriptions.get(i) == null || descriptions.get(i).length() == 0){
                throw new BadRequestException("Bad Request");
            }

            hm.put("SchoolName", schools.get(i));
            hm.put("Year", passingYears.get(i));
            hm.put("Description", descriptions.get(i));

            result.add(hm);
        }

        return result;
    }

    public List<HashMap<String, String>> getExperience(String experience){
        List<HashMap<String, String>> result = new ArrayList<>();

        String companyPattern = "company_name=(.*?)[,}]";
        String yearPattern = "passing_year=(.*?)[,}]";
        String respPattern = "responsibilities=(.*?)}";

        List<String> companyNames = new ArrayList<>();
        List<String> passingYears = new ArrayList<>();
        List<String> responsibilities = new ArrayList<>();

        Pattern regexPattern = Pattern.compile(companyPattern);
        Matcher matcher = regexPattern.matcher(experience);

        while (matcher.find()) {
            companyNames.add(matcher.group(1).trim());
        }

        regexPattern = Pattern.compile(yearPattern);
        matcher = regexPattern.matcher(experience);

        while (matcher.find()) {
            passingYears.add(matcher.group(1).trim());
        }

        regexPattern = Pattern.compile(respPattern);
        matcher = regexPattern.matcher(experience);

        while (matcher.find()) {
            responsibilities.add(matcher.group(1).trim());
        }


        if(companyNames.size() != passingYears.size() || companyNames.size() != responsibilities.size()){
            throw new BadRequestException("Bad Request");
        }

        for(int i = 0; i < companyNames.size(); i++){
            HashMap<String, String> hm = new HashMap<>();

            if(companyNames.get(i) == null || companyNames.get(i).length() == 0
            || passingYears.get(i) == null || passingYears.get(i).length() == 0
            || responsibilities.get(i) == null || responsibilities.get(i).length() == 0){
                throw new BadRequestException("Bad Request");
            }

            hm.put("CompanyName", companyNames.get(i));
            hm.put("Year", passingYears.get(i));
            hm.put("Description", responsibilities.get(i));

            result.add(hm);
        }

        return result;
    }

    public List<HashMap<String, String>> getAchievements(String achievements){
        List<HashMap<String, String>> result = new ArrayList<>();

        String fieldPattern = "field=(.*?)[,}]";
        String awardPattern = "awards=(.*?)}";

        List<String> fields = new ArrayList<>();
        List<String> awards = new ArrayList<>();

        Pattern regexPattern = Pattern.compile(fieldPattern);
        Matcher matcher = regexPattern.matcher(achievements);

        while (matcher.find()) {
            fields.add(matcher.group(1).trim());
        }

        regexPattern = Pattern.compile(awardPattern);
        matcher = regexPattern.matcher(achievements);

        while (matcher.find()) {
            awards.add(matcher.group(1).trim());
        }

        if(fields.size() != awards.size()){
            throw new BadRequestException("Bad Request");
        }

        for (int i = 0; i < fields.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();

            if(fields.get(i) == null || fields.get(i).length() == 0
            || awards.get(i) == null || awards.get(i).length() == 0){
                throw new BadRequestException("Bad Request");
            }

            hm.put("Type", fields.get(i));
            hm.put("Description", awards.get(i));

            result.add(hm);
        }

        return result;
    }
}
