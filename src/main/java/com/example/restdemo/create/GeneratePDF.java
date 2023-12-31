package com.example.restdemo.create;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;

import com.adobe.pdfservices.operation.pdfops.DocumentMergeOperation;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.DocumentMergeOptions;
import com.adobe.pdfservices.operation.pdfops.options.documentmerge.OutputFormat;

import com.example.restdemo.exception.*;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONObject;

public class GeneratePDF {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GeneratePDF.class);

    public void generatePDF(int id){
        try {

            String input_file = String.format("./rest-demo/src/main/resources/templates/Template%d.docx", id);

            String output_file = "./rest-demo/src/main/resources/generated/generatedResume.pdf";
            Files.deleteIfExists(Paths.get(output_file));

            Path jsonPath = Paths.get("./rest-demo/src/main/resources/generated/templateJSON.json");

            String json = new String(Files.readAllBytes(jsonPath));
            JSONObject jsonDataForMerge = new JSONObject(json);

            System.out.println("About to generate a PDF based on " + input_file + "\n");

            // Initial setup, create credentials instance.
            Map<String, String> env = System.getenv();

            Credentials credentials = Credentials.servicePrincipalCredentialsBuilder()
                    .withClientId(env.get("PDF_SERVICES_CLIENT_ID"))
                    .withClientSecret(env.get("PDF_SERVICES_CLIENT_SECRET"))
                    .build();


            // Create an ExecutionContext using credentials.
            ExecutionContext executionContext = ExecutionContext.create(credentials);

            DocumentMergeOptions documentMergeOptions = new DocumentMergeOptions(jsonDataForMerge, OutputFormat.PDF);

            DocumentMergeOperation documentMergeOperation = DocumentMergeOperation.createNew(documentMergeOptions);

            // Provide an input FileRef for the operation
            FileRef source = FileRef.createFromLocalFile(input_file);
            documentMergeOperation.setInput(source);

            // Execute the operation
            FileRef result = documentMergeOperation.execute(executionContext);

            // Save the result at the specified location
            result.saveAs(output_file);

            LOGGER.info("All Done");

        }
        catch (ServiceApiException | IllegalArgumentException | ServiceUsageException e){
            LOGGER.error("Unauthorised");

            throw new UnauthorizedException("Unauthorised");
        }
        catch (IOException | SdkException e) {
            LOGGER.error("Exception encountered while executing operation", e);

            throw new InternalServerErrorException("Internal Server Error");

        }
    }
}
