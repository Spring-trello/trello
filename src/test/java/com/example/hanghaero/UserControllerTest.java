// package com.example.hanghaero;
//
// import com.example.hanghaero.config.WebSecurityConfig;
// import com.example.hanghaero.controller.BoardController;
// import com.example.hanghaero.controller.UserController;
// import com.example.hanghaero.entity.Board;
// import com.example.hanghaero.entity.Col;
// import com.example.hanghaero.entity.User;
// import com.example.hanghaero.entity.UserRoleEnum;
// import com.example.hanghaero.filter.MockSpringSecurityFilter;
// import com.example.hanghaero.repository.BoardRepository;
// import com.example.hanghaero.repository.ColRepository;
// import com.example.hanghaero.repository.UserRepository;
// import com.example.hanghaero.security.UserDetailsImpl;
// import com.example.hanghaero.service.BoardService;
// import com.example.hanghaero.service.ColService;
// import com.example.hanghaero.service.UserService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.context.annotation.FilterType;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.test.context.web.WebAppConfiguration;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.context.WebApplicationContext;
//
// import java.security.Principal;
//
// import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
// @WebMvcTest(
// 	controllers = {UserController.class, ColService.class},
// 	excludeFilters = {
// 		@ComponentScan.Filter(
// 			type = FilterType.ASSIGNABLE_TYPE,
// 			classes = WebSecurityConfig.class
// 		)
// 	}
// )
// @WebAppConfiguration
// class UserControllerTest {
// 	private MockMvc mvc;
// 	private Principal mockPrincipal;
// 	@Autowired
// 	private WebApplicationContext context;
// 	@Autowired
// 	private ObjectMapper objectMapper;
// 	@MockBean
// 	UserService userService;
// 	@MockBean
// 	UserRepository userRepository;
// 	@MockBean
// 	BoardService boardService;
// 	@MockBean
// 	BoardRepository boardRepository;
// 	@MockBean
// 	ColService colService;
// 	@MockBean
// 	ColRepository colRepository;
//
// 	@BeforeEach
// 	public void setup() {
// 		mvc = MockMvcBuilders.webAppContextSetup(context)
// 			.apply(springSecurity(new MockSpringSecurityFilter()))
// 			.build();
// 	}
//
// 	private void mockUserSetup() {
// 		// Mock 테스트 유져 생성
// 		String email = "sollertia@sparta.com";
// 		String username = "sollertia4351";
// 		String password = "robbie1234";
// 		String phoneNumber = "010-1111-1111";
// 		String address = "null";
// 		UserRoleEnum role = UserRoleEnum.ADMIN;
// 		User testUser = new User(username, password, phoneNumber, address, email, role);
// 		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
// 		mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
// 	}
//
// 	@Test
// 	@DisplayName("로그인 Page")
// 	void test1() throws Exception {
// 		// when - then
// 		mvc.perform(get("/signin"))
// 			.andDo(print());
// 	}
//
// 	@Test
// 	@DisplayName("회원 가입 요청 처리")
// 	void test2() throws Exception {
// 		// given
// 		MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
// 		signupRequestForm.add("username", "sollertia4351");
// 		signupRequestForm.add("password", "robbie1234");
// 		signupRequestForm.add("email", "sollertia@sparta.com");
// 		signupRequestForm.add("phoneNumber", "010-1111-1111");
// 		signupRequestForm.add("address", "1234");
// 		signupRequestForm.add("admin", "false");
//
// 		// when - then
// 		mvc.perform(post("/signup")
// 				.params(signupRequestForm)
// 			)
// 			.andDo(print());
// 	}
//
//
// }