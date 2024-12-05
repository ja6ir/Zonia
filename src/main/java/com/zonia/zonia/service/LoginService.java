package com.zonia.zonia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zonia.zonia.entity.Login;
import com.zonia.zonia.repository.LoginRepository;

@Service
public class LoginService {
	   @Autowired
	    private LoginRepository repo;
	  
	    public Login login(String username, String password) {
	        Login user = repo.findByUsernameAndPassword(username, password);
	        return user;
	    }
}
