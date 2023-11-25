package com.example.hanghaero.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.hanghaero.auth.AuthInterceptor;
import com.example.hanghaero.dto.comment.CommentRequestDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.example.hanghaero.security.userdetails.UserDetailsServiceImpl;
import com.example.hanghaero.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class) // Mock, InjectMock 작동을 위해
@ActiveProfiles("test") // Test profile 적용
@WebMvcTest(
	controllers = {CommentController.class},
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = {WebMvcConfigurer.class, AuthInterceptor.class}
		)
	}
)
class CommentControllerTest {

	@MockBean
	UserDetailsImpl userDetails;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CommentService commentService;
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	private ObjectMapper objectMapper;
	private Principal mockPrincipal;

	@BeforeEach
	public void beforeEach() {
		objectMapper = new ObjectMapper();
		//mockPrincipal = new UsernamePasswordAuthenticationToken("username");
	}

	@Test
	@WithUserDetails(value = "email")
	void createCommentTest() throws Exception {

		given(userDetailsServiceImpl.loadUserByUsername("email")).willReturn(new UserDetailsImpl(
			User.builder().email("email").build()));
		given(userDetails.getPassword()).willReturn("password");
		mockMvc.perform(MockMvcRequestBuilders
				.post("/cards/1/comment")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.content(objectMapper.writeValueAsString(CommentRequestDto.builder().contents("contents").build()))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());

	}

	@Test
	void updateComment() {
	}

	@Test
	void deleteComment() {
	}

}