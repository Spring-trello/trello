package com.example.hanghaero.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
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

import com.example.hanghaero.controller.UserController;
import com.example.hanghaero.dto.user.SignInRequestDto;
import com.example.hanghaero.dto.user.SignUpRequestDto;
import com.example.hanghaero.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SignInTest {

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
		SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
			.email(email)
			.password(password)
			.name("테스트")
			.address("테스트")
			.phoneNumber("0000000000")
			.adminCheck(false)
			.build();

		userController.signup(signUpRequestDto);
		objectMapper = new ObjectMapper();
	}

	@AfterEach
	void afterEach() {
		userRepository.delete(userRepository.findByEmail(email).orElseThrow());
	}

	@Test
	void signInSuccessTest() throws Exception {
		SignInRequestDto signInRequestDto = SignInRequestDto.builder()
			.email(email)
			.password(password)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/processing-signin")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(Encoding.DEFAULT_CHARSET)
				.content(objectMapper.writeValueAsString(signInRequestDto)))
			.andExpect(status().isOk())
			.andExpect(result -> assertTrue(result.getResponse().getHeader("Authorization").startsWith("Bearer ")))
			.andDo(print());
	}

	@Test
	void signInFailureByWrongEmailTest() throws Exception {
		SignInRequestDto signInRequestDto = SignInRequestDto.builder()
			.email("wrong@wrong.com")
			.password(password)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/processing-signin")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(Encoding.DEFAULT_CHARSET)
				.content(objectMapper.writeValueAsString(signInRequestDto)))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertEquals("이메일 혹은 비밀번호가 틀렸습니다.", result.getResponse().getContentAsString()))
			.andDo(print());
	}

	@Test
	void signInFailureByWrongPasswordTest() throws Exception {
		SignInRequestDto signInRequestDto = SignInRequestDto.builder()
			.email("wrong@wrong.com")
			.password(password)
			.build();

		mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/processing-signin")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding(Encoding.DEFAULT_CHARSET)
				.content(objectMapper.writeValueAsString(signInRequestDto)))
			.andExpect(status().isBadRequest())
			.andExpect(result -> assertEquals("이메일 혹은 비밀번호가 틀렸습니다.", result.getResponse().getContentAsString()))
			.andDo(print());
	}

}
