package com.example.hanghaero.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.user.UpdateUserResponseDto;
import com.example.hanghaero.dto.user.UserRequestDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

	@Transactional
	public UpdateUserResponseDto updateUser(Long id, UserRequestDto userRequestDto, Long userId) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new EntityNotFoundException("존재하지않은 회원입니다.")
		);

		if (user.getId() != userId) {
			throw new IllegalArgumentException("본인 계정만 수정 할 수 있습니다.");
		}

		user.update(userRequestDto);
		return new UpdateUserResponseDto(user);
	}

}
