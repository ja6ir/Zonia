package com.zonia.zonia.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class JobPosting {
	@Id
    private String id;
    private String title;
    private String salary;
    private String experienceRequired;
    private List<String> keySkills;
    private String roleCategory;
    private String location;
    private String functionalArea;
    private String industry;
    private String role;
    private List<String> qualifications;
	public JobPosting(String id, String title, String salary, String experienceRequired, List<String> keySkills,
			String roleCategory, String location, String functionalArea, String industry, String role,
			List<String> qualifications) {
		super();
		this.id = id;
		this.title = title;
		this.salary = salary;
		this.experienceRequired = experienceRequired;
		this.keySkills = keySkills;
		this.roleCategory = roleCategory;
		this.location = location;
		this.functionalArea = functionalArea;
		this.industry = industry;
		this.role = role;
		this.qualifications = qualifications;
	}
	public JobPosting() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getExperienceRequired() {
		return experienceRequired;
	}
	public void setExperienceRequired(String experienceRequired) {
		this.experienceRequired = experienceRequired;
	}
	public List<String> getKeySkills() {
		return keySkills;
	}
	public void setKeySkills(List<String> keySkills) {
		this.keySkills = keySkills;
	}
	public String getRoleCategory() {
		return roleCategory;
	}
	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFunctionalArea() {
		return functionalArea;
	}
	public void setFunctionalArea(String functionalArea) {
		this.functionalArea = functionalArea;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getQualifications() {
		return qualifications;
	}
	public void setQualifications(List<String> qualifications) {
		this.qualifications = qualifications;
	}
	public void setCrawlTimestamp(String string) {
		// TODO Auto-generated method stub
		
	} 
	@Override
    public String toString() {
        return "JobPosting{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", salary='" + salary + '\'' +
                ", experienceRequired='" + experienceRequired + '\'' +
                ", keySkills=" + keySkills +
                ", roleCategory='" + roleCategory + '\'' +
                ", location='" + location + '\'' +
                ", functionalArea='" + functionalArea + '\'' +
                ", industry='" + industry + '\'' +
                ", role='" + role + '\'' +
                ", qualifications=" + qualifications +
                '}';
    }
}
    

