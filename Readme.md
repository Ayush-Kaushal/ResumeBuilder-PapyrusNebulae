# ResumeBuilder-PapyrusNebulae

This project contains the submission for Adobe PapyrusNebula Hackathon Round 2. 

## Requirements

For building and running the application you need:

- [Java](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org)
- [Adobe ID](https://acrobatservices.adobe.com/dc-integration-creation-app-cdn/main.html?api=document-generation-api)

## Running the application

- Using credentials from ```pdfservices-api-credentials.json``` set environment variables __PDF_SERVICES_CLIENT_ID__ and __PDF_SERVICES_CLIENT_SECRET__.
- To run the application execute the main method in ```src/main/java/com/example/restdemo/RestDemoApplication.java```.
- Open __Postman__ or command prompt and run the sample curl command present in ```ResumeBuilder Curl Request```.
- Change the values as per your requirements.

## Project Structure

- There are 3 different types of templates available(1, 2 and 3). You can choose any one of them by simply changing __template_id__ in post request.
- ```com.example.restdemo.controller``` package contains the code to process the incoming API requests.
- ```com.example.restdemo.create``` package contains two files:
  - ```GeneratePDF.java``` generates resume in the pdf format from the specified template and input raw json data.
  - ```GenerateJSON.java``` generates the converts the input raw json data into the desired json format.
- ```com.example.restdemo.exception``` package contains the custom exception handling files.
  - ```BadRequestException.java``` for handling Bad Request errors(400).
  - ```UnauthorizedException.java``` for handling Unauthorised access errors(401).
  - ```TemplateNotFoundException.java``` for handling Template Not Found error(404).
  - ```InternalServerErrorException.java``` for handling Internal Server errors(500).
  - ```ResumeBuilderAPIException.java``` is a class to represent the desired error details.
  - ```ResumeBuilderAPIExceptionHandler.java``` is a class to handle the exception and return a custom error response.
- ```RestDemoApplication.java``` contains the main method to run the application.
- ```src/main/resources/templates``` directory contains the resume templates.
- ```src/main/resources/generated``` directory is for storing the json data and the generated resume pdf file.
