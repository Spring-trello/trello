package com.example.hanghaero.dto.user;

import com.example.hanghaero.annotation.Password;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class SignInRequestDto {
	@Email
	private String email;
	@Password
	private String password;
}
