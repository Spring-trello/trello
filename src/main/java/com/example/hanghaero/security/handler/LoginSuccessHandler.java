package com.example.hanghaero.security.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "LoginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response, Authentication authentication)
		throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		String targetUrl = determineTargetUrl(authentication);
		log.info("targetUrl : " + targetUrl);
		if (response.isCommitted()) {
			log.info("이미 commit된 response 이므로 이 handler에서 수행하지않음 : " + targetUrl);
			return;
		}
		log.info("redirect 요청 보냄");
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {

		Map<String, String> roleTargetUrlMap = new HashMap<>();
		roleTargetUrlMap.put("ROLE_USER", "/index.html");
		roleTargetUrlMap.put("ROLE_ADMIN", "/admin.html");

		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			String authorityName = grantedAuthority.getAuthority();
			log.info("authorityName : " + authorityName);
			if (roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
		}

		throw new IllegalStateException();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		log.info("authentication 제거");
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}

