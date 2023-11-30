package com.example.hanghaero.exception.user;

public class PasswordNotMatchedException extends RuntimeException {
	public static final String errorMsg = "비밀번호가 일치하지 않습니다.";

	public PasswordNotMatchedException() {
		super(errorMsg);
	}
}
