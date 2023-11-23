package com.example.hanghaero.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hanghaero.error.JwtErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JwtExceptionHandlerFilter")
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (SecurityException | MalformedJwtException e) {
			log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
			setErrorResponse(response, JwtErrorCode.TOKEN_INVALID_SIGNATURE);
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
			setErrorResponse(response, JwtErrorCode.TOKEN_EXPIRED);
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
			setErrorResponse(response, JwtErrorCode.TOKEN_UNSUPPORTED);
		} catch (Exception e) {
			log.error("e :" + e);
			setErrorResponse(response, JwtErrorCode.TOKEN_EMPTY_CLAIM);
		}
	}

	private void setErrorResponse(HttpServletResponse response, JwtErrorCode jwtErrorCode) {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(jwtErrorCode.getHttpStatus());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		CustomErrorResponse errorResponse = new CustomErrorResponse(
			jwtErrorCode.getCode(), jwtErrorCode.getMessage());
		try {
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		} catch (IOException e) {
			log.error("e.toString() :" + e.toString());
		}
	}

	@Data
	public static class CustomErrorResponse {
		private final Integer code;
		private final String message;
	}
}