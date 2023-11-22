package com.example.hanghaero.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.hanghaero.auth.AuthInterceptor;
import com.example.hanghaero.auth.WebMvcConfig;
import com.example.hanghaero.dto.CommentRequestDto;
import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.factory.WithMockCustomUser;
import com.example.hanghaero.security.UserDetailsImpl;
import com.example.hanghaero.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class) // Mock, InjectMock 작동을 위해
@ActiveProfiles("test") // Test profile 적용
@WebMvcTest(
	controllers = {CommentController.class},
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = {WebMvcConfig.class, AuthInterceptor.class}
		)
	}
)
class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	CommentService commentService;

	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void beforeEach() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity())
			.build();
	}

	@Test
	@WithMockCustomUser
	void createCommentTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders
				.post("/cards/1/comment")
				.content(objectMapper.writeValueAsString(getCommentRequestDto()))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

	}

	@Test
	void updateComment() {
	}

	@Test
	void deleteComment() {
	}

	public UserDetailsImpl getUserDetails() {
		UserDetailsImpl userDetails = new UserDetailsImpl(getUser());
		return userDetails;
	}

	public User getUser() {
		User user = new User();
		user.setEmail("email");
		user.setRole(UserRoleEnum.USER);
		return user;
	}

	public CommentRequestDto getCommentRequestDto() {
		return new CommentRequestDto("contents");
	}

}