package com.zonia.zonia.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.zonia.zonia.entity.Login;
import com.zonia.zonia.entity.Register;
import com.zonia.zonia.service.LoginService;
import com.zonia.zonia.service.RegisterService;

import jakarta.servlet.http.HttpSession;



@Controller
public class ZoniaController {
	
	@GetMapping("/")
	public String index(){
	return "login"; }
	
	@GetMapping("/register.html")
	public String registerpage(){
	return "register"; }

	@GetMapping("/login.html")
	public String loginpage(){
	return "login"; }
	
	@Autowired
	RegisterService regService;
	@GetMapping("/home.html")
	
	public String home(Model model,HttpSession session){
		
	String username = (String) session.getAttribute("username");
	
	if (username == null) {
         
         return "redirect:/login.html"; 
         
     }
	
	model.addAttribute("name", regService.getNameByUsername(username));

	return "home"; }
	
	@GetMapping("/contact.html")
	public String contact(HttpSession session){
		
		String username = (String) session.getAttribute("username");
		
		if (username == null) {
	         
	         return "redirect:/login.html"; 
	     }
		return "contact"; }
	
	@GetMapping("/about.html")
	public String about(HttpSession session){
		
		String username = (String) session.getAttribute("username");
		
		if (username == null) {
	         
	         return "redirect:/login.html"; 
	     }
		return "about"; }
	
	@Autowired
	private RegisterService regservice;
	@PostMapping("/reguser")
	public String reguser( @ModelAttribute Register user) {
		regservice.reguser(user);
		return "redirect:/login.html";

}
	@Autowired
	private LoginService userService;
	@PostMapping("/login")
	public String login(@ModelAttribute("user") Login user,HttpSession session ) {

	Login oauthUser = userService.login(user.getUsername(), user.getPassword());



	if(Objects.nonNull(oauthUser)) 
	{    
		session.setAttribute("username", user.getUsername());

	    return "redirect:/home.html";

	    
	} else {
		System.out.println("username"+ user.getUsername());
	    return "redirect:/login.html";

	}

	}
	 @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login.html";
	    }
	 @GetMapping("/profile")
		public String profile(){
		return "profile"; 
	 }
	 @GetMapping("/resume")
		public String resume(HttpSession session){
			
			String username = (String) session.getAttribute("username");
			
			if (username == null) {
		         
		         return "redirect:/login.html"; 
		     }
			String filepath = regService.getFilepathByUsername(username);
			return "0"; 
	 }
	}
