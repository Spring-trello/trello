package com.example.hanghaero.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.hanghaero.dto.user.SignUpRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	private String inviteColumn;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role;

	@OneToMany(mappedBy = "user")
	private List<BoardUser> boardUserList = new ArrayList<>();
	// @OneToMany(mappedBy = "user",  cascade = CascadeType.PERSIST, orphanRemoval = true)
	// private List<Comment> commentList = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "user",  cascade = CascadeType.PERSIST, orphanRemoval = true)
	// private List<Card> cardList = new ArrayList<>();

	public User(SignUpRequestDto signupRequestDto) {
		this.email = signupRequestDto.getEmail();
		this.password = signupRequestDto.getPassword();
		this.username = signupRequestDto.getUsername();
		this.phoneNumber = signupRequestDto.getPhoneNumber();
		this.address = signupRequestDto.getAddress();
		this.role = signupRequestDto.getRole();
	}

	public void update(SignUpRequestDto signupRequestDto) {
		this.phoneNumber = signupRequestDto.getPhoneNumber();
		this.address = signupRequestDto.getAddress();
	}
}
