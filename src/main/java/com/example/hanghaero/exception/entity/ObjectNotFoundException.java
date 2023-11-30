package com.example.hanghaero.exception.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
public class ObjectNotFoundException extends RuntimeException {
	// 예외 처리 로직
}
