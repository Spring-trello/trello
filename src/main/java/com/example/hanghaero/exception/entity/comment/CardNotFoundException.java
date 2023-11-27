package com.example.hanghaero.exception.entity.comment;

public class CardNotFoundException extends RuntimeException {
	public static final String errorMsg = "해당 카드가 존재하지 않습니다.";

	public CardNotFoundException() {
		super(errorMsg);
	}
}
