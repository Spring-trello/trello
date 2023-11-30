package com.example.hanghaero.exception.entity;

public class ColumnNotFoundException extends RuntimeException {
	public static String errorMsg = "해당 컬럼이 존재하지 않습니다.";

	public ColumnNotFoundException() {
		super(errorMsg);
	}
}
