package com.zonia.zonia.repository;

import com.opencsv.CSVReader;
import com.zonia.zonia.entity.JobPosting;
import com.zonia.zonia.service.JobProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class JobRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobRepository.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public List<JobPosting> getAllJobs() {
        List<JobPosting> jobs = new ArrayList<>();

        try {
            Resource resource = resourceLoader.getResource("classpath:job.csv");
            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
            CSVReader csvReader = new CSVReader(reader);

            // Skip header line
            csvReader.readNext();

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                // Check that line has the correct number of fields
                if (nextLine.length >= 11) {
                    try {
                        JobPosting job = new JobPosting();
                        job.setId(nextLine[0]); // ?
                        job.setCrawlTimestamp(nextLine[1]); // Crawl Timestamp
                        job.setTitle(nextLine[2]); // Job Title
                        job.setSalary(nextLine[3]); // Job Salary
                        job.setExperienceRequired(nextLine[4]); // Job Experience Required
                        job.setKeySkills(Arrays.asList(nextLine[5].split("\\|"))); // Key Skills
                        job.setRoleCategory(nextLine[6]); // Role Category
                        job.setLocation(nextLine[7]); // Location
                        job.setFunctionalArea(nextLine[8]); // Functional Area
                        job.setIndustry(nextLine[9]); // Industry
                        job.setRole(nextLine[10]); // Role
                        // New field
                        if (nextLine.length > 11) {
                            job.setQualifications(Arrays.asList(nextLine[11].split("\\|"))); // Qualifications
                        }

                        // Check that each field is not null or empty
                        if (job.getId() != null && !job.getId().isEmpty() &&
                            job.getTitle() != null && !job.getTitle().isEmpty() &&
                            job.getSalary() != null && !job.getSalary().isEmpty() &&
                            job.getExperienceRequired() != null && !job.getExperienceRequired().isEmpty() &&
                            job.getKeySkills() != null && !job.getKeySkills().isEmpty() &&
                            job.getRoleCategory() != null && !job.getRoleCategory().isEmpty() &&
                            job.getLocation() != null && !job.getLocation().isEmpty() &&
                            job.getFunctionalArea() != null && !job.getFunctionalArea().isEmpty() &&
                            job.getIndustry() != null && !job.getIndustry().isEmpty() &&
                            job.getRole() != null && !job.getRole().isEmpty()) {
                            jobs.add(job);
                        }

                    } catch (Exception e) {
                        LOGGER.error("Error processing line", e);
                    }
                } else {
                    LOGGER.error("Line does not have the correct number of fields: " + Arrays.toString(nextLine));
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error reading jobs from CSV file", e);
            throw new JobProcessingException("Error reading jobs from CSV file", e);
        }

        return jobs;
    }
   

}
