package com.example.hanghaero.annotation.validation;

import com.example.hanghaero.annotation.Password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> { // 1

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) { // 2
		if (value == null) {
			return false;
		}

		return value.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}");
	}
}
