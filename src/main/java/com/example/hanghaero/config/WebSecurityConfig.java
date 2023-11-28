package com.example.hanghaero.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

import com.example.hanghaero.filter.ExceptionHandlerFilter;
import com.example.hanghaero.filter.HttpLoggingFilter;
import com.example.hanghaero.security.JwtUtil;
import com.example.hanghaero.security.filter.JwtAuthenticationFilter;
import com.example.hanghaero.security.filter.JwtAuthorizationFilter;
import com.example.hanghaero.security.handler.AuthenticationEntryPointImpl;
import com.example.hanghaero.security.handler.LoginSuccessHandler;
import com.example.hanghaero.security.userdetails.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public AccessDeniedHandlerImpl accessDeniedHandler() {
		return new AccessDeniedHandlerImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public HttpLoggingFilter httpLoggingFilter() {
		HttpLoggingFilter filter = new HttpLoggingFilter();
		return filter;
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	@Bean
	public ExceptionHandlerFilter jwtExceptionHandlerFilter() throws Exception {
		return new ExceptionHandlerFilter();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
		filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
		return filter;
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}

	@Bean
	public AuthenticationEntryPointImpl authenticationEntryPoint() {
		return new AuthenticationEntryPointImpl();
	}

	@Bean

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 설정
		http.csrf(AbstractHttpConfigurer::disable);

		// 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
		http.sessionManagement(
			(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(httpLoggingFilter(), DisableEncodeUrlFilter.class);

		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(
				PathRequest.toStaticResources().atCommonLocations())
			.permitAll() // resources 접근 허용 설정
			.requestMatchers("/")
			.permitAll() // 메인 페이지 요청 허가
			.requestMatchers("/users/**")
			.permitAll()
			.requestMatchers("/columns/boards/**")
			.permitAll()
			.requestMatchers("/error") // 인증 Exception이 발생할경우 /error로 간다.
			.permitAll() // admin 권한 확인은 Interceptor에서 수행
			.requestMatchers("/columns/board/**")
			.permitAll()
			.anyRequest()
			.authenticated() // 그 외 모든 요청 인증처리

		);

		http.formLogin((formLogin) ->
			formLogin
				.loginPage("/users/signin")
				.loginProcessingUrl("/processing-signin")
				.successHandler(loginSuccessHandler()));

		http.exceptionHandling((exception) ->
			exception.authenticationEntryPoint(authenticationEntryPoint()));

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtExceptionHandlerFilter(), JwtAuthorizationFilter.class);

		return http.build();
	}
}
