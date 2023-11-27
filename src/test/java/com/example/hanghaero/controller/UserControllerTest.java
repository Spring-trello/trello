package com.example.hanghaero.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.hanghaero.dto.user.SignUpRequestDto;
import com.example.hanghaero.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private final String email = "a@a.com";
	private final String password = "qwer1234!";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserController userController;

	@Autowired
	UserRepository userRepository;
	ObjectMapper objectMapper;

	@BeforeEach
	void beforeEach() {
		objectMapper = new ObjectMapper();

	}

	@Test
	void SignUpEmailValidationFailTest() throws Exception {
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.email("wrong email")
			.password(password)
			.name("테스트")
			.address("테스트")
			.phoneNumber("0000000000")
			.adminCheck(false)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(Encoding.DEFAULT_CHARSET)
				.content(objectMapper.writeValueAsString(signUpRequestDto)))
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(
					result.getResponse().getContentAsString().contains("must be a well-formed email address")))
			.andDo(print());
	}

	@Test
	void SignUpPasswordValidationFailByLengthConditionTest() throws Exception {
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.email(email)
			.password("a1!")
			.name("테스트")
			.address("테스트")
			.phoneNumber("0000000000")
			.adminCheck(false)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/users/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(Encoding.DEFAULT_CHARSET)
				.content(objectMapper.writeValueAsString(signUpRequestDto)))
			.andExpect(status().isBadRequest())
			.andExpect(
				result -> assertTrue(
					result.getResponse().getContentAsString().contains("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")))
			.andDo(print());
	}
}
