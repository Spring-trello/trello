package com.example.hanghaero.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hanghaero.dto.user.UserRequestDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(UserRequestDto userRequestDto) {
		String encodedPwd = passwordEncoder.encode(userRequestDto.getPassword());
		User user = new User(userRequestDto);
		user.setPassword(encodedPwd);
		userRepository.save(user);
	}
}
