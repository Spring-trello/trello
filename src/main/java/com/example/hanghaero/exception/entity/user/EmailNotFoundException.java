package com.example.hanghaero.exception.entity.user;

public class EmailNotFoundException extends RuntimeException {
	public static final String errorMsg = "가입되지 않은 이메일입니다.";

	public EmailNotFoundException() {
		super(errorMsg);
	}
}
