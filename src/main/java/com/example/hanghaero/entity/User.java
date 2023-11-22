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

	public User(SignUpRequestDto requestDto) {
		this.email = requestDto.getEmail();
		this.password = requestDto.getPassword();
		this.username = requestDto.getUsername();
		this.phoneNumber = requestDto.getPhoneNumber();
		this.address = requestDto.getAddress();
		this.role = requestDto.getRole();
	}

	public void update(SignUpRequestDto requestDto) {
		this.phoneNumber = requestDto.getPhoneNumber();
		this.address = requestDto.getAddress();
	}
}
