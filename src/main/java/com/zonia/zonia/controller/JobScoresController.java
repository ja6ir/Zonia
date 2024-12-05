package com.zonia.zonia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zonia.zonia.entity.JobPosting;
import com.zonia.zonia.repository.JobRepository;
import com.zonia.zonia.service.JobMatchingService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JobScoresController {

	 @Autowired
	 private JobMatchingService jobMatchingService;
	 
	
	
	 @PostMapping("/processJobScores")
	    public ModelAndView processJobScores(@RequestBody Map<String, Double> jobScores) {
	        // Filter out entries with null or invalid job IDs or scores
	        jobScores.entrySet().removeIf(entry -> entry.getKey() == null || entry.getKey().isEmpty() || entry.getValue() == null);

	        // Create a map of JobPosting to Double
	        Map<JobPosting, Double> jobDetailsWithScores = new LinkedHashMap<>();

	        for (Map.Entry<String, Double> entry : jobScores.entrySet()) {
	            String jobId = entry.getKey().startsWith("- ") ? entry.getKey().substring(2) : entry.getKey();
	            Double score = entry.getValue();
	            if(score >=5) {
	            JobPosting jobDetails = jobMatchingService.getJobById(jobId);

	            // Check if jobDetails is not null before adding it to the map
	            if (jobDetails != null ) {
	                jobDetailsWithScores.put(jobDetails, score);
	            }
	        }
	        }
	        // Sort the map by value in descending order
	        jobDetailsWithScores = jobDetailsWithScores.entrySet().stream()
	            .sorted(Map.Entry.<JobPosting, Double>comparingByValue().reversed())
	            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	        // Create a ModelAndView object
	        ModelAndView mav = new ModelAndView("joblist");  // "jobPostings" is the name of your Thymeleaf template

	        // Add the job details to the model
	        mav.addObject("jobDetailsWithScores", jobDetailsWithScores);

	        // Return the ModelAndView object
	        return mav;
	    }
	 @GetMapping("/apply")
		public String apply(){
		return "<center>Sorry!,THE LINK IS UNDER DEVELOPMENT</center>"; 
		}
	}