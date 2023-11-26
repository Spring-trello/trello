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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 로직에서 Entity 사용은 허용하고, 무분별한 객체 생성 제한
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	// username -> name field명 변경
	// SpringSecurity 내부적으로 사용되는 username은 현재 User Entity의 email 필드이다.
	// 혼동이 오지 않기 위해 name으로 이름 변경
	@Column(nullable = false)
	private String name;

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
		this.name = signupRequestDto.getName();
		this.phoneNumber = signupRequestDto.getPhoneNumber();
		this.address = signupRequestDto.getAddress();
	}

	// Test를 위해 빌더 패턴 사용
	@Builder
	public User(String email, String password, String name, String phoneNumber, String address, UserRoleEnum role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
	}

	public void update(SignUpRequestDto signupRequestDto) {
		this.phoneNumber = signupRequestDto.getPhoneNumber();
		this.address = signupRequestDto.getAddress();
	}

	public void update(String password) {
		this.password = password;
	}

	public void update(UserRoleEnum role) {
		this.role = role;
	}
}
