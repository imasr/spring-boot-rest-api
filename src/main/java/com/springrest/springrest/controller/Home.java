package com.springrest.springrest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

	@GetMapping("/")
	public ResponseEntity<String> welcomeMessage() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Welcome to the Rest API homepage of Java Spring Boot");
	}
}
