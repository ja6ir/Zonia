package com.zonia.zonia.service;

import com.zonia.zonia.entity.JobPosting;
import com.zonia.zonia.entity.Register;
import com.zonia.zonia.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {
    @Autowired
    private JobRepository jobRepository;

    public List<JobPosting> matchJobsToUser(Register user) {
        List<JobPosting> allJobs = jobRepository.getAllJobs(); // Get all jobs from the repository
        
        // Calculate scores for each job based on user's qualifications, skills, experience, and location
        List<JobScore> jobScores = allJobs.stream()
                .map(job -> calculateJobScore(job, user))
                .collect(Collectors.toList());
        
        // Sort job scores by score in descending order
        jobScores.sort((js1, js2) -> Double.compare(js2.getScore(), js1.getScore()));
        
        // Convert job scores to job postings and filter out jobs with score less than or equal to 5
        List<JobPosting> matchedJobs = jobScores.stream()
                .filter(js -> js.getScore() > 14)
                .map(JobScore::getJobPosting)
                .collect(Collectors.toList());
        
        return matchedJobs;
    }


    private JobScore calculateJobScore(JobPosting job, Register user) {
        double score = 0.0;

        for (String skill : user.getSkillList()) {
            for (String jobSkill : job.getKeySkills()) {
                if (jobSkill.toLowerCase().contains(skill.toLowerCase())) {
                    score += 1.0; // Increment score if user skill matches job requirement
                }
            }
        }


        // Score based on experience
        // Assuming experience is represented as a range, e.g., "0 - 2 yrs"
        String experienceRangeString = job.getExperienceRequired();
        int minExperience;
        int maxExperience;

        if (experienceRangeString.contains(" - ")) {
            String[] experienceRange = experienceRangeString.split(" - ");
            minExperience = parseExperience(experienceRange[0]);
            maxExperience = parseExperience(experienceRange[1]);
        	

        	// Log the value of experienceString

            
        } else {
            // Handle the case where the experience range string does not contain " - "
            minExperience = parseExperience(experienceRangeString);
            maxExperience = minExperience; // Or any other default value you prefer
        }
    	


        int userExperience = user.getExperience();
        if (userExperience >= minExperience && userExperience <= maxExperience) {
            // Increment score based on difference between user's experience and minimum experience
            int experienceScore = minExperience - userExperience + 12;
            score += experienceScore;
        }


        return new JobScore(job, score);
    }

    // Helper method to parse experience from the range string
 // Helper method to parse experience from the range string
    private int parseExperience(String experienceString) {
    	
    	
        

        // Check if the input string is empty
        if (experienceString.isEmpty()) {
            return 0; // Or any default value you prefer
        }

        // Extract numeric value from the string
        String numericValue = experienceString.replaceAll("[^\\d]", "");
        
        
        
        // Check if the numeric value string is not empty
        if (!numericValue.isEmpty()) {
        
        	
            return Integer.parseInt(numericValue);
        } else {
        	
        	

            return 0; // Or any default value you prefer
        }
    }


    // Define JobScore class
    private class JobScore {
        private JobPosting jobPosting;
        private double score;

        public JobScore(JobPosting jobPosting, double score) {
            this.jobPosting = jobPosting;
            this.score = score;
        }

        public JobPosting getJobPosting() {
            return jobPosting;
        }

        public double getScore() {
            return score;
        }
    }
    
    public JobPosting getJobById(String jobId) {
        List<JobPosting> allJobs =jobRepository.getAllJobs();
    	

        // Get all jobs from the repository

        for (JobPosting job : allJobs) {
            if (job.getId().equals(jobId)) {
                return job; // Return the job if the ID matches
            }
        }

        return null; // Return null if no job with the given ID is found
    }
    
    

}
