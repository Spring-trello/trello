package com.example.hanghaero.exception.user;

public class PasswordNotAvailableByRuleException extends RuntimeException {
	public static final String errorMsg = "비밀번호는 8자 이상 15자 이하로 영문, 숫자, 특수문자를 포함해야합니다.";

	public PasswordNotAvailableByRuleException() {
		super(errorMsg);
	}
}
