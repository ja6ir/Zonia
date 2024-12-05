package com.zonia.zonia.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Register {

@Id
private String username;
private String password;
private String name;
private String age;
private String gender;
private String email;
private String mobile;
private String qualifications;
private String location;
private int experience;
private String skills;
private String otherPreference;
private String filepath;

public Register(String username, String password, String name, String age, String gender, String email, String mobile,
		String qualifications, String location, int experience, String skills, String otherPreference,String filepath) {
	super();
	this.username = username;
	this.password = password;
	this.name = name;
	this.age = age;
	this.gender = gender;
	this.email = email;
	this.mobile = mobile;
	this.qualifications = qualifications;
	this.location = location;
	this.experience = experience;
	this.skills = skills;
	this.otherPreference = otherPreference;
	this.filepath = filepath;

}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
public String getQualifications() {
	return qualifications;
}
public void setQualifications(String qualifications) {
	this.qualifications = qualifications;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public int getExperience() {
	return experience;
}
public void setExperience(int experience) {
	this.experience = experience;
}
public String getSkills() {
	return skills;
}
public void setSkills(String skills) {
	this.skills = skills;
}
public String getOtherPreference() {
	return otherPreference;
}
public void setOtherPreference(String otherPreference) {
	this.otherPreference = otherPreference;
}

public String getFilepath() {
	return filepath;
}
public void setFilepath(String filepath) {
	this.filepath = filepath;
}
public Register() {
	super();
	// TODO Auto-generated constructor stub
}




public List<String> getSkillList() {
    // Assuming skills is a string stored in a field named skills
    String skillsString = this.skills; // Assuming skills is a field in the Register class
    return Arrays.stream(skillsString.split(","))
                 .map(String::trim)
                 .collect(Collectors.toList());


}
}
