package com.zonia.zonia.controller;

import org.apache.tika.exception.TikaException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.zonia.zonia.service.ResumeProcessService;
import com.zonia.zonia.service.TikaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Controller

public class FileController {

    @Autowired
    private TikaService tikaService;

    @Autowired
    private ResumeProcessService resumeProcessService;
    

    private static String UPLOAD_DIR = "src/main/resources/uploads/";


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file , Model model) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
           
            String relativeFilePath = filePath.toString();

            // Call the extractTextFromFile method after the file has been uploaded
           
            String resumeDetailsJson = resumeProcessService.openAiServiceCall(relativeFilePath);
            System.out.println("resume= " + resumeDetailsJson);
            
            JSONObject resumeDetails = new JSONObject(resumeDetailsJson);
            
            System.out.println("test  "+resumeDetails.toString(4)); // pretty print with 4 spaces indentation


            

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
            
            System.out.println("name: " + name);
            System.out.println("age: " + age);
            System.out.println("qualifications: " + qualifications);
            System.out.println("skills: " + skills);
            System.out.println("experience: " + experience);
            System.out.println("location: " + location);
            System.out.println("gender: " + gender);
            System.out.println("otherPreference: " + otherPreference);

            model.addAttribute("name",name);
            model.addAttribute("age", age);
            model.addAttribute("qualifications", qualifications);
            model.addAttribute("skills", skills);
            model.addAttribute("experience", experience);
            model.addAttribute("location", location);
            model.addAttribute("gender", gender);
            model.addAttribute("otherPreference", otherPreference);
            model.addAttribute("filepath", relativeFilePath);

            // Return the name of your Thymeleaf template
            return "profile";
            
        } catch (Exception e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }



    @GetMapping("/extractText/{filePath}")
    public String extractText(@PathVariable String filePath) throws IOException, TikaException, SAXException {
        String relativeFilePath = filePath;
        String result = resumeProcessService.openAiServiceCall(relativeFilePath);
        System.out.println("result= " + result);
        return result;
    }
    
}
