package com.example.hanghaero.exception.entity.User;

public class DuplicateEmailException extends IllegalArgumentException {
	public DuplicateEmailException() {
		super("이미 가입된 이메일입니다.");
	}
}
