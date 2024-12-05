package com.zonia.zonia.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zonia.zonia.entity.JobPosting;
import com.zonia.zonia.entity.Register;
import com.zonia.zonia.repository.RegisterRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class OpenAiService {

    @Value("${apikey}")
    private String apikey;

    @Value("${modelid}")
    private String modelid;

    @Value("${url}")
    private String url;
    
    @Autowired
    private RegisterService regService;

    private final RestTemplate restTemplate;
    
    @Autowired
    private JobMatchingService jobMatchingService;
    
    @Autowired
    private RegisterRepository u;

    public OpenAiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String openAiServiceCall(String[] userInputs,String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apikey);
        
        

        // Build messages array with a system message and user inputs
        JSONArray messagesArray = new JSONArray();
        String name = regService.getNameByUsername(username);
        String age = regService.getByAgename(username);
        String qualifications = regService.getQaulificationByUsername(username);	
        String skills = regService.getSkillsByUsername(username);
        int experience = regService.getExperienceByUsername(username);
        String location = regService.getLocationByUsername(username);
        String gender = regService.getGenderByUsername(username);
        String otherPreference =regService.getOtherPreferenceByUsername(username);

        //first stage of filtering jobs
        
        Register user = new Register();
        user.setAge(u.findAgeByUsername(username));
        user.setSkills(u.findSkillsByUsername(username));
        user.setExperience(u.findExperienceByUsername(username));
        
        List<JobPosting> matchedJobs = jobMatchingService.matchJobsToUser(user);
        
        StringBuilder matchedJobsStr = new StringBuilder();
        for (JobPosting job : matchedJobs) {
            matchedJobsStr.append("Id: ").append(job.getId())
            			  .append("Job Title: ").append(job.getTitle())	
                          .append(", Salary: ").append(job.getSalary())
                          .append(", Experience Required: ").append(job.getExperienceRequired())
                          .append(", Key Skills: ").append(job.getKeySkills())
                          .append(", Role Category: ").append(job.getRoleCategory())
                          .append(", Location: ").append(job.getLocation())
                          .append(", Functional Area: ").append(job.getFunctionalArea())
                          .append(", Industry: ").append(job.getIndustry())
                          .append(", Role: ").append(job.getRole())
                          .append("\n");
        }
        
        System.out.println("name- "+name);
        System.out.println("age- "+age);
        System.out.println("q- "+qualifications);
        System.out.println("s- "+skills);
        System.out.println("e- "+experience);
        System.out.println("l- "+location);
        System.out.println("g- "+gender);
        System.out.println("o- "+otherPreference);
        System.out.println("job title: " + matchedJobsStr.toString());

        
        String systemnote = "You are interacting with Zonia, a sophisticated job matching platform. The user details are as follows: \n"
                + "1) Name: " + name + "\n"
                + "2) Age: " + age + "\n"
                + "3) Qualifications: " + qualifications + "\n"
                + "4) Skills: " + skills + "\n"
                + "5) Experience: " + experience + "\n"
                + "6) Location: " + location + "\n"
                + "7) Other Preferences: " + otherPreference + "\n"
                + "8) Gender: " + gender + "\n"
                + "The user's responses in this conversation contain answers to the following questions: \n"
                + "1) What type of projects or tasks do you find most fulfilling and enjoyable? \n"
                + "2) What work environment brings out the best in you? \n"
                + "3) What industries or sectors are you particularly interested in? \n"
                + "4) Do you prefer a structured or flexible work schedule? \n"
                + "5) What career goals do you aim to achieve in the next few years? \n"
                + "Based on the provided information, recommend a fitting job profile. Also, provide a list of matched jobs from the csv dataset: " + matchedJobsStr.toString() + "\n"
                + "Process all the information and provide the response strictly in the following format:\n"
                + "1) **Analysis Report:** [A brief analysis report]\n"
                + "2) **Matched Jobs:** [JobId : score (Score for each job from the list of matched jobs from the csv dataset in a scale of 0 to 10, score 0 for unmatching job )]\n"
                + "Score these matching jobs in a scale of 0-10 by analyzing all the user details and user answer to the question, score 0 for an unfit job.\n"
                + "Additionally, include a personalized greeting by addressing the user with their name in the response.";

        JSONObject systemMessageObject = new JSONObject();
        systemMessageObject.put("role", "system");
        systemMessageObject.put("content",systemnote);
        messagesArray.put(systemMessageObject);

        // Add user messages
        for (String userInput : userInputs) {
            JSONObject messageObject = new JSONObject();
            messageObject.put("role", "user");
            messageObject.put("content", userInput);
            messagesArray.put(messageObject);
        }

        // Build request body
        JSONObject requestBodyObject = new JSONObject();
        requestBodyObject.put("model", modelid);
        requestBodyObject.put("messages", messagesArray);
        String requestBody = requestBodyObject.toString();

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        String responseFromAi = restTemplate.postForObject(url, request, String.class);

        JSONObject responseJson = new JSONObject(responseFromAi);

        // Print the response
        System.out.println("response content =" + responseJson.toString());

        // Return the entire response from the AI as a JSON object
        return responseJson.toString();
    }


    // Helper method to extract "content" from the response JSON
    /* private String extractContentFromResponse(String jsonResponse) {
        // Assuming you are using some JSON library, you can parse the JSON and extract the "content"
        // For example, using org.json.JSONObject
        JSONObject responseObject = new JSONObject(jsonResponse);
        JSONArray choices = responseObject.getJSONArray("choices");
        JSONObject choice = choices.getJSONObject(0);
        JSONObject message = choice.getJSONObject("message");
        return  */
}
