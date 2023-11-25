package com.example.hanghaero.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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

import com.example.hanghaero.filter.HttpLoggingFilter;
import com.example.hanghaero.filter.JwtExceptionHandlerFilter;
import com.example.hanghaero.jwt.JwtUtil;
import com.example.hanghaero.security.JwtAuthenticationFilter;
import com.example.hanghaero.security.JwtAuthorizationFilter;
import com.example.hanghaero.security.UserDetailsServiceImpl;

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
	public JwtExceptionHandlerFilter jwtExceptionHandlerFilter() throws Exception {
		return new JwtExceptionHandlerFilter();
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
			.requestMatchers("/login")
			// 실제로는 JwtAuthenticationFilter가 /api/user/signin url에서 로그인 요청을 받지만 스프링 시큐리티 기본 url이 /login이라서 버그 방지로 넣음
			.permitAll()
			.requestMatchers(HttpMethod.POST, "/users/**") // '/api/user/'로 시작하는 요청 모두 접근 허가
			.permitAll()
			.requestMatchers("/error") // 인증 Exception이 발생할경우 /error로 간다.
			.permitAll()
			.requestMatchers("/admin/**")
			.hasRole("ADMIN") // admin Role
			.anyRequest()
			.authenticated() // 그 외 모든 요청 인증처리
		);

		http.formLogin(Customizer.withDefaults());

		// JWT 예외처리 필터 추가
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
		http.addFilterBefore(jwtExceptionHandlerFilter(), JwtAuthorizationFilter.class);

		return http.build();
	}
}
