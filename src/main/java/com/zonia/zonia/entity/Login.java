package com.zonia.zonia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="register")
public class Login {
    
    @Id
    private String username;
    private String password;
	public Login(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Login() {
		super();
		// TODO Auto-generated constructor stub
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
   

}
