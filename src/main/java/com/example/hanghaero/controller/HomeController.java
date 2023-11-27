package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/admin")
	public ResponseEntity<?> adminTest() {
		return new ResponseEntity<>("admin", HttpStatus.OK);
	}
}
