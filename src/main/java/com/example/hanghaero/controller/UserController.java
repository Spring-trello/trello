package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.user.UserRequestDto;
import com.example.hanghaero.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity signup(@Valid @RequestBody UserRequestDto userRequestDto) {
		userService.signup(userRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하였습니다.");
	}

	@Post
}
