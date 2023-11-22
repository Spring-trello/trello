package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hanghaero.dto.user.UpdateUserResponseDto;
import com.example.hanghaero.dto.user.UserRequestDto;
import com.example.hanghaero.security.UserDetailsImpl;
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

	@PutMapping("/users/{id}")
	public ResponseEntity<UpdateUserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto,
		@PathVariable Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();

		return new ResponseEntity<>(userService.updateUser(id, userRequestDto, userId), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUser().getId();
		userService.deleteUser(id, userId);
		return ResponseEntity.status(HttpStatus.OK).body("회원이 삭제되었습니다.");
	}
}
