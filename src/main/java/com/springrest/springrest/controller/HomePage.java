package com.springrest.springrest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

	@GetMapping("/")
	public ResponseEntity<String> welcomeMessage() {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body("<h1>Rest API Java Spring Boot</h1>");
	}
}
