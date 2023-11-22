package com.example.hanghaero.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.user.SignupRequestDto;
import com.example.hanghaero.dto.user.UserUpdateResponseDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupRequestDto signupRequestDto) {
		String encodedPwd = passwordEncoder.encode(signupRequestDto.getPassword());
		User user = new User(signupRequestDto);
		user.setPassword(encodedPwd);

		userRepository.findByEmail(user.getEmail()).ifPresent(p -> {
			throw new RuntimeException("이미 가입한 이메일 입니다.");
		});

		userRepository.save(user);
	}

	@Transactional
	public UserUpdateResponseDto updateUser(Long id, SignupRequestDto signupRequestDto, Long userId) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않은 회원입니다.")
		);

		if (user.getId() != userId) {
			throw new IllegalArgumentException("본인 계정만 수정 할 수 있습니다.");
		}

		user.update(signupRequestDto);
		return new UserUpdateResponseDto(user);
	}

	public void deleteUser(Long id, Long userId) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않는 회원입니다.")
		);

		if (user.getId() != userId) {
			throw new IllegalArgumentException("계정 삭제에 대한 권한이 없습니다.");
		}

		userRepository.delete(user);
	}

}
