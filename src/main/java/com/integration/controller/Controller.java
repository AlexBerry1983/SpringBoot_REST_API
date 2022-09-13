package com.integration.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/content/{id}")
	public String content(@PathVariable String id) {
		return "Found content with key: "+id;
	}
	
	@PostMapping("/content")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public String postContent() {
		return UUID.randomUUID().toString();
	}
}
