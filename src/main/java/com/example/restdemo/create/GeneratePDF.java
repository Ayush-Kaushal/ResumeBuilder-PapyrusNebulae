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

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            Credentials credentials = Credentials.servicePrincipalCredentialsBuilder()
                    .withClientId("9e52a716944a446c8c2ddaf6b0f66706")
                    .withClientSecret("p8e-4wS_-ckwfLtGVrhjQ_73_0Hmx7W7w3LN")
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

            System.out.println("All Done");

        } catch (ServiceApiException | IOException | SdkException | ServiceUsageException e) {
            LOGGER.error("Exception encountered while executing operation", e);
        }
    }
}
