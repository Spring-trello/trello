package com.example.hanghaero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class ValidExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();

		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append(" 올바르지 않은 입력 :");
			builder.append(fieldError.getRejectedValue());
			builder.append('\n');
			builder.append(fieldError.getField());
			builder.append(" (은)는 ");
			builder.append(fieldError.getDefaultMessage());
			builder.append('\n').append('\n');

		}
		return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	}

}
