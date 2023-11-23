package com.example.hanghaero.exception.entity.User;

public class EmailNotFoundException extends RuntimeException {
	public EmailNotFoundException() {
		super("가입되지 않은 이메일입니다.");
	}
}
