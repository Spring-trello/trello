package com.example.hanghaero.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.hanghaero.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
	private final UserRepository userRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		IOException {
		System.out.println("[ ----- AuthInterceptor : preHandle ----- ]");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = null;

		while (iter.hasNext()) {
			auth = iter.next();
			System.out.println("[USER AUTHENTICATION : " + auth.getAuthority() + " ]");
		}

		HandlerMethod handlerMethod = (HandlerMethod)handler;
		final String method = request.getMethod();
		System.out.println("[request method : " + method + " ]");
		if (auth.getAuthority() == "ADMIN") {
			System.out.println(" [ " + SecurityContextHolder.getContext().getAuthentication().getName() + "관리자 입니다. ]");
			return true;
		}
		log.info("관리자만 접근 가능한 페이지 입니다.");
		response.sendError(401, "관리자만 접근 가능한 페이지 입니다.");
		return false;
	}
}
