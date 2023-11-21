package com.example.hanghaero.dto.user;

import com.example.hanghaero.entity.UserRoleEnum;

import lombok.Getter;

@Getter
public class UserResponseDto {
	private String email;
	private String password;
	private String username;
	private String phoneNumber;
	private String address;
	private UserRoleEnum role;
}
