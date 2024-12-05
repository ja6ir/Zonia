package com.zonia.zonia.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import org.json.JSONObject;
import org.json.JSONArray;

@Service
public class ResumeProcessService {

    @Value("${apikey}")
    private String apikey;

    @Value("${modelid}")
    private String modelid;

    @Value("${url}")
    private String url;

    private final RestTemplate restTemplate;
    private final TikaService tikaService;

    public ResumeProcessService(RestTemplate restTemplate, TikaService tikaService) {
        this.restTemplate = restTemplate;
        this.tikaService = tikaService;
    }

    public String openAiServiceCall(String filePath) throws IOException, TikaException, SAXException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apikey);

        // Extract text from the resume file
        String resume = tikaService.extractTextFromFile(filePath);

        // Build the request body for OpenAI API
        JSONArray messagesArray = new JSONArray();
        String systemnote = "Process the text extracted from the resume and provide the details strictly in the following format:\n" +
        	    "1) **Name:** [Full Name]\n" +
        	    "2) **Age:** [Age]\n" +
        	    "3) **Qualifications:** [List of qualifications]\n" +
        	    "4) **Skills:** [List of skills - single word attributes]\n" +
        	    "5) **Experience:** [number of year]\n" +
        	    "6) **Location:** [Location]\n" +
        	    "7) **Gender:** [Gender]\n" +
        	    "8) **Other Preference:** [Other preferences]"+
        	    "if a field has more than one attribute, seperate it using comma, dont use \n between them.if the data has no information about a field set its value as 'null'";
        
        messagesArray.put(new JSONObject().put("role", "system").put("content", systemnote + resume));

        JSONObject requestBodyObject = new JSONObject();
        requestBodyObject.put("model", modelid);
        requestBodyObject.put("messages", messagesArray);
        String requestBody = requestBodyObject.toString();

        // Send the request to OpenAI API
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        String responseFromAi = restTemplate.postForObject(url, request, String.class);

        // Parse the response from OpenAI API
        JSONObject responseJson;
        try {
            responseJson = new JSONObject(responseFromAi);
        } catch (Exception e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return null; // Return null or handle the error appropriately
        }

        // Print the response (you can remove this if not needed)
        System.out.println("Response content: " + responseJson.toString());

        // Extract resume details
        JSONObject resumeDetails = extractResumeDetails(responseJson);

        // Print the extracted details
        System.out.println("Extracted Resume :");
        System.out.println(resumeDetails.toString());

        // Return the entire response from the AI as a JSON object
        return resumeDetails.toString();
    }

    private JSONObject extractResumeDetails(JSONObject jsonResponse) {
        // Extract the message content
        JSONObject message = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message");
        String content = message.getString("content");

        // Data cleaning process
        content = content.replace("**", "").trim(); // Remove "**"
        content = content.replaceAll(" +", " "); // Replace multiple spaces with a single space

        // Split the content by new line characters
        String[] lines = content.split("\n");

        // Initialize a JSONObject to store the extracted details
        JSONObject resumeDetails = new JSONObject();

        // Variable to store the last field name
        String lastFieldName = "";

        // Iterate through each line and extract the relevant information
        for (String line : lines) {
            // Remove leading and trailing spaces
            line = line.trim();

            // Log each line for debugging
            System.out.println("Processing line: " + line);

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Check if the line contains a ":"
            if (line.contains(":")) {
                // Split each line by ": " to get the field name and value
                String[] parts = line.split(": ", 2);
                if (parts.length != 2) {
                    // Log lines that do not match the expected format
                    System.out.println("Line does not match expected format: " + line);
                    continue; // Skip lines that do not match the expected format
                }

                // Extract field name and value
                String fieldName = parts[0].trim();
                String fieldValue = parts[1].trim();
               
                if (fieldValue.equals("null")) {
                    fieldValue = null;  // Set fieldValue to null
                } else {
                    fieldValue += "%";  // Add "%" for separation
                }

                // Log the extracted field for debugging
                System.out.println("Extracted field: " + fieldName + " -> " + fieldValue);

                // Put the extracted field into the resumeDetails JSONObject
                resumeDetails.put(fieldName, fieldValue);

                // Update the last field name
                lastFieldName = fieldName;
            } else if (line.startsWith("-")) {
                // This line is a sub-point, add it to the last field
                String existingValue = resumeDetails.optString(lastFieldName, "");
                existingValue += "\n" + line;
                resumeDetails.put(lastFieldName, existingValue);
            }
        }

        // Log the final resume details object for debugging
        System.out.println("Final extracted resume details: " + resumeDetails);

        // After extracting the resume details...

        String[] nameParts = resumeDetails.optString("1) Name", "").split("%");
        String name = nameParts[0].trim();

        String[] ageParts = resumeDetails.optString("2) Age", "").split("%");
        String age = ageParts[0].trim();

        String[] qualificationsParts = resumeDetails.optString("3) Qualifications", "").split("%");
        String qualifications = qualificationsParts[0].trim();

        String[] skillsParts = resumeDetails.optString("4) Skills", "").split("%");
        String skills = skillsParts[0].trim();

        String[] experienceParts = resumeDetails.optString("5) Experience", "").split("%");
        String experience = experienceParts[0].trim();

        String[] locationParts = resumeDetails.optString("6) Location", "").split("%");
        String location = locationParts[0].trim();

        String[] genderParts = resumeDetails.optString("7) Gender", "").split("%");
        String gender = genderParts[0].trim();

        String[] otherPreferenceParts = resumeDetails.optString("8) Other Preference", "").split("%");
        String otherPreference = otherPreferenceParts[0].trim();

        
        
        

        return resumeDetails;
    }









}
