package com.example.hanghaero.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.hanghaero.dto.user.SignInRequestDto;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.security.JwtUtil;
import com.example.hanghaero.security.userdetails.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;

	@Qualifier("handlerExceptionResolver")
	@Autowired
	private HandlerExceptionResolver resolver;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/processing-signin"); // 실제로 로그인을 수행하는 url
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			SignInRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
				SignInRequestDto.class);
			log.info("requestDto : " + requestDto);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.getEmail(),
					requestDto.getPassword(),
					null
				)
			);
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) {
		try {
			String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
			UserRoleEnum role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getRole();

			String token = jwtUtil.createToken(username, role);
			log.info("token : " + token);

			// 응답 헤더에 토큰 추가
			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

			// 응답 상태 코드 및 메시지 설정
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("로그인에 성공하였습니다.");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("IOException 발생 : " + e.getMessage());
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) {
		resolver.resolveException(request, response, null, failed);
	}
}
