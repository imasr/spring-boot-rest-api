package com.springrest.springrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
	
	@GetMapping("/")
	public String welcomeMessage() {
		return "WELCOME TO THE REST API JAVA SPRING BOOT";
	}
}
