package com.example.hanghaero.error;

import org.springframework.http.HttpStatus;

public enum JwtErrorCode {
	TOKEN_EXPIRED(Error.TOKEN_EXPIRED_HTTPSTATUS, Error.TOKEN_EXPIRED_CODE, Error.TOKEN_EXPIRED_MESSAGE),
	TOKEN_INVALID_SIGNATURE(Error.TOKEN_INVALID_SIGNATURE_HTTPSTATUS, Error.TOKEN_INVALID_SIGNATURE_CODE,
		Error.TOKEN_INVALID_SIGNATURE_MESSAGE),
	TOKEN_UNSUPPORTED(Error.TOKEN_UNSUPPORTED_HTTPSTATUS, Error.TOKEN_UNSUPPORTED_CODE,
		Error.TOKEN_UNSUPPORTED_MESSAGE),
	TOKEN_EMPTY_CLAIM(Error.TOKEN_EMPTY_CLAIM_HTTPSTATUS, Error.TOKEN_EMPTY_CLAIM_CODE,
		Error.TOKEN_EMPTY_CLAIM_MESSAGE),

	TOKEN_ROLE_NOTFOUND(Error.TOKEN_ROLE_NOTFOUND_HTTPSTATUS, Error.TOKEN_ROLE_NOTFOUND_CODE,
		Error.TOKEN_ROLE_NOTFOUND_MESSAGE);
	private final String message;
	private final int code;
	private final int httpStatus;

	JwtErrorCode(int httpStatus, int code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public int getHttpStatus() {
		return this.httpStatus;
	}

	public String getMessage() {
		return this.message;
	}

	public int getCode() {
		return this.code;
	}

	public static class Error {
		// TODO: _CODE 변수에 2023-11-23 BE-FE 간 알아볼 수 있는 코드 지정 필요
		public static final int TOKEN_EXPIRED_HTTPSTATUS = HttpStatus.UNAUTHORIZED.value();
		public static final int TOKEN_EXPIRED_CODE = HttpStatus.UNAUTHORIZED.value();
		public static final String TOKEN_EXPIRED_MESSAGE = "로그인 정보가 만료되었습니다.";

		public static final int TOKEN_INVALID_SIGNATURE_HTTPSTATUS = HttpStatus.BAD_REQUEST.value();
		public static final int TOKEN_INVALID_SIGNATURE_CODE = HttpStatus.BAD_REQUEST.value();
		public static final String TOKEN_INVALID_SIGNATURE_MESSAGE = "로그인 정보가 잘못되었습니다.";

		public static final int TOKEN_UNSUPPORTED_HTTPSTATUS = HttpStatus.BAD_REQUEST.value();
		public static final int TOKEN_UNSUPPORTED_CODE = HttpStatus.BAD_REQUEST.value();
		public static final String TOKEN_UNSUPPORTED_MESSAGE = "로그인 정보가 잘못되었습니다.";

		public static final int TOKEN_EMPTY_CLAIM_HTTPSTATUS = HttpStatus.BAD_REQUEST.value();
		public static final int TOKEN_EMPTY_CLAIM_CODE = HttpStatus.BAD_REQUEST.value();
		public static final String TOKEN_EMPTY_CLAIM_MESSAGE = "로그인 정보가 잘못되었습니다.";

		public static final int TOKEN_ROLE_NOTFOUND_HTTPSTATUS = HttpStatus.NOT_FOUND.value();
		public static final int TOKEN_ROLE_NOTFOUND_CODE = HttpStatus.NOT_FOUND.value();
		public static final String TOKEN_ROLE_NOTFOUND_MESSAGE = "유저의 권한 설정이 잘못되었습니다.";

	}
}
