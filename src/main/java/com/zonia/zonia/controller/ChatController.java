package com.zonia.zonia.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zonia.zonia.entity.JobPosting;
import com.zonia.zonia.entity.Register;
import com.zonia.zonia.repository.RegisterRepository;
import com.zonia.zonia.service.JobMatchingService;
import com.zonia.zonia.service.OpenAiService;

import jakarta.servlet.http.HttpSession;

@Controller

public class ChatController {

    @Autowired
    OpenAiService aiService;

    @PostMapping("/submitAnswers")
    public ResponseEntity<String> submitAnswers(@RequestParam("userInputs") String[] userInputs,HttpSession session) {
      
    	String username = (String) session.getAttribute("username");
    	String responseFromAi = aiService.openAiServiceCall(userInputs,username);
    	
    

        return ResponseEntity.ok(responseFromAi);
    }
    @Autowired
    private JobMatchingService jobMatchingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    
    @Autowired
   private RegisterRepository u;
    @GetMapping("/matchJobs")
    public ModelAndView matchJobsToUser(HttpSession session) {
    	
    	String username = (String) session.getAttribute("username");
    	if (username == null) {
	         
	 
    	}
	         
        Register user = new Register();
        
      
        user.setAge(u.findAgeByUsername(username));
        user.setSkills(u.findSkillsByUsername(username));
        user.setExperience(u.findExperienceByUsername(username));
        // Set other fields as needed

        LOGGER.info("Matching jobs for user: {}", user);
        List<JobPosting> matchedJobs = jobMatchingService.matchJobsToUser(user);
        
        
        System.out.println("match - "+ matchedJobs);
        LOGGER.info("Matched jobs: {}", matchedJobs);
        ModelAndView mav = new ModelAndView("jobdemo");
        mav.addObject("data", matchedJobs);
        
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
        
        System.out.println("job title: " + matchedJobsStr.toString());

        

        return mav;
    }
    
    

}