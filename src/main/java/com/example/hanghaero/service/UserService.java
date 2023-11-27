package com.example.hanghaero.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.example.hanghaero.dto.user.SignUpRequestDto;
import com.example.hanghaero.dto.user.UserUpdateResponseDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.exception.entity.user.DuplicateEmailException;
import com.example.hanghaero.exception.entity.user.ManagerSecretKeyNotMatchedException;
import com.example.hanghaero.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${manager.secret.key}")
	private String managerSecretKey;

	public void signup(SignUpRequestDto signupRequestDto) {
		String encodedPwd = passwordEncoder.encode(signupRequestDto.getPassword());
		User user = new User(signupRequestDto);
		user.update(encodedPwd);

		userRepository.findByEmail(user.getEmail()).ifPresent((p) -> {
			throw new DuplicateEmailException();
		});

		UserRoleEnum role = UserRoleEnum.USER;
		String inputAdminKey = signupRequestDto.getAdminKey();

		if (signupRequestDto.isAdminCheck()) {
			if (StringUtils.equals(managerSecretKey, inputAdminKey)) {
				role = UserRoleEnum.ADMIN;
			} else {
				throw new ManagerSecretKeyNotMatchedException();
			}
		}

		user.update(role);
		userRepository.save(user);
	}

	@Transactional
	public UserUpdateResponseDto updateUser(Long id, SignUpRequestDto signupRequestDto, Long userId) {
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
