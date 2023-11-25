package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.user.SignUpRequestDto;
import com.example.hanghaero.dto.user.UserUpdateResponseDto;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.example.hanghaero.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@PostMapping("/user/signup")
	public ResponseEntity signup(@Valid @RequestBody SignUpRequestDto signupRequestDto) {
		userService.signup(signupRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입에 성공하였습니다.");
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<UserUpdateResponseDto> updateUser(@RequestBody SignUpRequestDto signupRequestDto,
		@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();

		return new ResponseEntity<>(userService.updateUser(id, signupRequestDto, userId), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		userService.deleteUser(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body("회원이 삭제되었습니다.");
	}
}
