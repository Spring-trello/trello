package com.example.hanghaero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hanghaero.exception.entity.AuthorityNotSufficientException;
import com.example.hanghaero.exception.entity.CardNotFoundException;
import com.example.hanghaero.exception.entity.ColumnNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

	@ExceptionHandler(CardNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<?> handleCardNotFoundException() {
		return new ResponseEntity<>(CardNotFoundException.errorMsg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ColumnNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<?> handleColumnNotFoundException() {
		return new ResponseEntity<>(ColumnNotFoundException.errorMsg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthorityNotSufficientException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	protected ResponseEntity<?> handleAuthorityNotSufficientException() {
		log.info("AuthorityNotSufficientException.errorMsg : " + AuthorityNotSufficientException.errorMsg);
		return new ResponseEntity<>(AuthorityNotSufficientException.errorMsg, HttpStatus.UNAUTHORIZED);
	}

}
