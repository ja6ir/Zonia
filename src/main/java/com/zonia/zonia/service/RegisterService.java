package com.zonia.zonia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zonia.zonia.entity.Register;
import com.zonia.zonia.repository.RegisterRepository;

@Service
public class RegisterService {
	
	@Autowired
private RegisterRepository reguser;
	
	 public String getNameByUsername(String username) {
	        return reguser.findNameByUsername(username);
	    }
	 public String getByAgename(String username) {
	        return reguser.findAgeByUsername(username);
	    }
	
	 public String getQaulificationByUsername(String username) {
	        return reguser.findQualificationByUsername(username);
	    }
	
	 public String getGenderByUsername(String username) {
	        return reguser.findGenderByUsername(username);
	        
	    }
	 public int getExperienceByUsername(String username) {
	        return reguser.findExperienceByUsername(username);
	 }
	      
	 public String getSkillsByUsername(String username) {
		        return reguser.findSkillsByUsername(username);
	        }
	 
	 public String getLocationByUsername(String username) {
	        return reguser.findLocationByUsername(username);
     }
	 public String getFilepathByUsername(String username) {
	        return reguser.findFilepathByUsername(username);
  }
	
	public void reguser(Register user) {
		reguser.save(user);
	}
	public String getOtherPreferenceByUsername(String username) {
		// TODO Auto-generated method stub
        return reguser.findOtherPreferenceByUsername(username);
	}
}
