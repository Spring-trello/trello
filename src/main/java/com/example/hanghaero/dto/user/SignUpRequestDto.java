package com.example.hanghaero.dto.user;

import com.example.hanghaero.annotation.Password;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpRequestDto {

	@Email
	private String email;
	@Password
	private String password;
	private String name;
	private String phoneNumber;
	private String address;
	private boolean adminCheck;
	private String adminKey;
}
