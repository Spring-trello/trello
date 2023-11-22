package com.example.hanghaero.entity;

public enum UserRoleEnum {
	USER(Authority.USER),  // USER 권한
	ADMIN(Authority.ADMIN);  // ADMIN 권한

	private final String authority;

	UserRoleEnum(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return this.authority;
	}

	public static class Authority {
		public static final String USER = "USER";
		public static final String ADMIN = "ADMIN";
	}
}
