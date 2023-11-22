package com.example.hanghaero.factory;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.example.hanghaero.entity.User;
import com.example.hanghaero.entity.UserRoleEnum;
import com.example.hanghaero.security.UserDetailsImpl;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		User user = new User();
		user.setEmail("email");
		user.setUsername("username");
		user.setRole(UserRoleEnum.USER);
		UserDetails principal = new UserDetailsImpl(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password",
			principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}

}
