package com.example.hanghaero.dto.user;

import com.example.hanghaero.entity.User;

import lombok.Getter;

@Getter
public class UpdateUserResponseDto {
	private String phoneNumber;
	private String address;

	public UpdateUserResponseDto(User user) {
		this.phoneNumber = user.getPhoneNumber();
		this.address = user.getAddress();
	}
}
