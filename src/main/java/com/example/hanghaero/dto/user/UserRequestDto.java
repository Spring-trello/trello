package com.example.hanghaero.dto.user;

import com.example.hanghaero.annotation.Password;
import com.example.hanghaero.entity.UserRoleEnum;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserRequestDto {

	@Email
	private String email;
	@Password
	private String password;
	private String username;
	private String phoneNumber;
	private String address;
	private UserRoleEnum role;
}
