package com.example.hanghaero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.hanghaero.exception.entity.user.DuplicateEmailException;
import com.example.hanghaero.exception.entity.user.EmailNotFoundException;
import com.example.hanghaero.exception.entity.user.PasswordNotAvailableByRuleException;
import com.example.hanghaero.exception.entity.user.PasswordNotMatchedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateEmailException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handleDuplicateEmailException() {
		return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handleEmailNotFoundException() {
		return new ResponseEntity<>("가입되지 않은 이메일입니다.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PasswordNotAvailableByRuleException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handlePasswordNotAvailableByRuleException() {
		return new ResponseEntity<>("비밀번호는 8자 이상 15자 이하로 영문, 숫자, 특수문자를 포함해야합니다.", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PasswordNotMatchedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handlePasswordNotMatchedException() {
		return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
	}
}
