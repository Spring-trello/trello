package com.example.hanghaero.exception.entity.user;

import org.springframework.security.access.AccessDeniedException;

public class UserNotHasAdminRoleException extends AccessDeniedException {
	public static final String errorMsg = "관리자만 요청할 수 있습니다.";

	public UserNotHasAdminRoleException() {
		super(errorMsg);
	}

}
