package com.example.hanghaero.exception.entity.user;

public class DuplicateEmailException extends IllegalArgumentException {
	public static final String errorMsg = "이미 가입된 이메일입니다.";

	public DuplicateEmailException() {
		super(errorMsg);
	}

}
