package com.example.hanghaero.dto.user;

import com.example.hanghaero.annotation.Password;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
	@Email
	private String email;
	@Password
	private String password;
}
