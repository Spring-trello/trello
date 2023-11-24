// package com.example.hanghaero.controller;
//
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.context.annotation.FilterType;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
// import com.example.hanghaero.auth.AuthInterceptor;
// import com.example.hanghaero.dto.board.BoardRequestDto;
// import com.example.hanghaero.factory.WithMockCustomUser;
// import com.example.hanghaero.service.BoardService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// @ExtendWith(SpringExtension.class)
// @ActiveProfiles("test")
// @WebMvcTest(controllers = {BoardController.class}, excludeFilters = {
// 	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebMvcConfigurer.class,
// 		AuthInterceptor.class})})
// class BoardControllerTest {
//
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private BoardService boardService;
// 	private ObjectMapper objectMapper;
//
// 	@BeforeEach
// 	public void beforeEach() {
// 		objectMapper = new ObjectMapper();
// 	}
//
// 	@Test
// 	@WithMockCustomUser(email = "email")
// 	void createBoard() throws Exception {
//
// 		BoardRequestDto boardRequestDto = BoardRequestDto.builder()
// 			.name("name")
// 			.bgColor("bgColor")
// 			.description("description")
// 			.build();
//
// 		mockMvc.perform(MockMvcRequestBuilders.post("/boards")
// 				.with(SecurityMockMvcRequestPostProcessors.csrf())
// 				.content(objectMapper.writeValueAsString(boardRequestDto))
// 				.contentType(MediaType.APPLICATION_JSON))
// 			.andExpect(status().isOk())
// 			.andDo(print());
// 	}
//
// }