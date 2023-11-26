package com.example.hanghaero.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "AuthenticationEntryPointImpl")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

	@Qualifier("handlerExceptionResolver")
	@Autowired
	private HandlerExceptionResolver resolver;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.info("authException.getMessage() : " + authException.getMessage());
		resolver.resolveException(request, response, null, authException);
	}
}
