package com.example.hanghaero.dto.mail;

import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;

@Getter
public class MailRequestDto {
	private String email;
	private String boardUrl;
}
