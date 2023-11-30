package com.example.hanghaero.exception.entity;

public class AuthorityNotSufficientException extends RuntimeException {
	public static String errorMsg = "";

	public AuthorityNotSufficientException(String thing, String operation) {
		super(thing + " 을(를) " + operation + "할 권한이 없습니다." + thing + " 생성자 혹은 보드 생성자만 삭제할 수 있습니다.");
		errorMsg = this.getMessage();

	}
}
