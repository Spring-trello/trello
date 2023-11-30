package com.example.hanghaero.exception.user;

public class ManagerSecretKeyNotMatchedException extends IllegalArgumentException {
	public static String errorMsg = "관리자 암호가 틀렸습니다.";

	public ManagerSecretKeyNotMatchedException() {
		super(errorMsg);
	}
}
