package com.example.hanghaero.entity;

import com.example.hanghaero.dto.user.UserRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "users")
@Entity
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	public User(UserRequestDto userRequestDto) {
		this.email = userRequestDto.getEmail();
		this.password = userRequestDto.getPassword();
		this.username = userRequestDto.getUsername();
		this.phoneNumber = userRequestDto.getPhoneNumber();
		this.address = userRequestDto.getAddress();
		this.role = userRequestDto.getRole();
	}
}
