package com.springrest.springrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@GetMapping("/")
	public String welcome() {
		return "WELCOME TO THE REST API JAVA SPRING BOOT";
	}
	
	@GetMapping("/home")
	public String home() {
		return "Hello Ashish";
	}

}
