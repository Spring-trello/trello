package com.example.hanghaero.dto.user;

import com.example.hanghaero.annotation.Password;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SigninRequestDto {

	@Email
	private String email;
	@Password
	private String password;
}
