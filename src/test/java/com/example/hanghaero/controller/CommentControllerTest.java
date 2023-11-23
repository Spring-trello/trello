// package com.example.hanghaero.controller;
//
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.http.MediaType;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
// import com.example.hanghaero.security.UserDetailsServiceImpl;
// import com.example.hanghaero.service.CommentService;
//
// @ActiveProfiles("test") // Test profile 적용
// @SpringBootTest
// @AutoConfigureMockMvc
// class CommentControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
// 	@MockBean
// 	private CommentService commentService;
//
// 	@BeforeEach
// 	public void beforeEach() {
//
// 	}
//
// 	@Test
// 	void createCommentTest() throws Exception {
// 		mockMvc.perform(MockMvcRequestBuilders.post("/cards/1/comment")
// 			.contentType(MediaType.APPLICATION_JSON)
// 			.characterEncoding("UTF-8")).andExpect(status().isOk());
// 	}
//
// 	@Test
// 	void updateComment() {
// 	}
//
// 	@Test
// 	void deleteComment() {
// 	}
//
// 	@TestConfiguration
// 	@EnableWebSecurity
// 	public static class TestSecurityConfig {
// 		@Bean
// 		public UserDetailsServiceImpl userDetailsServiceImpl() {
// 			return null;
// 		}
// 	}
//
// }