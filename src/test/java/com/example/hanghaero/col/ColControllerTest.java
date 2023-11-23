package com.example.hanghaero.col;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import com.example.hanghaero.auth.AuthInterceptor;
import com.example.hanghaero.controller.ColController;
import com.example.hanghaero.dto.column.ColRequestDto;
import com.example.hanghaero.dto.column.ColResponseDto;
import com.example.hanghaero.service.ColService;
import com.fasterxml.jackson.databind.ObjectMapper;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {ColController.class})
public class ColControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private ColService colService;
	@MockBean
	private AuthInterceptor authInterceptor;
	@Test
	//@ExtendWith(MockitoExtension.class) //메소드가 Mockito를 사용함을 명시적으로 알림.
	@WithMockUser //Spring security에 설정한 인증 정보를 제공
	@DisplayName("보드에 칼럼 등록")
	void test1() throws Exception{
		//any() : 모든 매개 변수에 대하여 같은 행동을 하는 Mock 객체 어떠한 변수가 들어와도 같은 객체 반환
		given(colService.createColumn(any(),any()))
			.willReturn(
				ColResponseDto.builder()
					.title("MockTest2")
					.build()
			);
		//String title = "Mockmvc Test";
		ColRequestDto requestDto = ColRequestDto.builder().title("MockTest2").build();
		String postInfo = objectMapper.writeValueAsString(requestDto);
		mockMvc.perform(post("/board/2/columns").with(csrf())
				.content(postInfo)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value("MockTest2"))
			.andDo(print());
	}
}
