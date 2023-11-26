package com.example.hanghaero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hanghaero.exception.entity.user.DuplicateEmailException;
import com.example.hanghaero.exception.entity.user.EmailNotFoundException;
import com.example.hanghaero.exception.entity.user.ManagerSecretKeyNotMatchedException;
import com.example.hanghaero.exception.entity.user.PasswordNotAvailableByRuleException;
import com.example.hanghaero.exception.entity.user.PasswordNotMatchedException;
import com.example.hanghaero.exception.entity.user.UserNotHasAdminRoleException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j(topic = "GlobalExceptionHandler")
public class GlobalExceptionHandler {

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
		}
		return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateEmailException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handleDuplicateEmailException() {
		return new ResponseEntity<>(DuplicateEmailException.errorMsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmailNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handleEmailNotFoundException() {
		return new ResponseEntity<>(EmailNotFoundException.errorMsg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PasswordNotAvailableByRuleException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handlePasswordNotAvailableByRuleException() {
		return new ResponseEntity<>(PasswordNotAvailableByRuleException.errorMsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PasswordNotMatchedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public ResponseEntity<?> handlePasswordNotMatchedException() {
		return new ResponseEntity<>(PasswordNotMatchedException.errorMsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ManagerSecretKeyNotMatchedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleManagerSecretKeyNotMatchedException() {
		return new ResponseEntity<>(ManagerSecretKeyNotMatchedException.errorMsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotHasAdminRoleException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleUserNotHasAdminRoleException() {
		return new ResponseEntity<>(UserNotHasAdminRoleException.errorMsg, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleBadCredentialsException() {
		return new ResponseEntity<>("이메일 혹은 비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleInternalAuthenticationServiceException() {
		return new ResponseEntity<>("요청에 문제가 발생했습니다. 다시 요청해주세요.", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleUsernameNotFoundException() {
		return new ResponseEntity<>("존재하지 않는 이메일입니다.", HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleAuthenticationCredentialsNotFoundException() {
		return new ResponseEntity<>("AUTHENTICATION_CREDENTIALS_NOT_FOUND", HttpStatus.UNAUTHORIZED);
	}

}
