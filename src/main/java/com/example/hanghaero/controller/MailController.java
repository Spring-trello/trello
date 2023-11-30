package com.example.hanghaero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hanghaero.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;
	private final ObjectMapper objectMapper;

	@ResponseBody
	@PostMapping("/mail")
	public String MailSend(@RequestBody String invite) throws JsonProcessingException {
		JsonNode jsonNode = objectMapper.readTree(invite);
		String email = jsonNode.get("email").asText();
		String boardUrl = jsonNode.get("url").asText();

		int number = mailService.sendMail(email, boardUrl);
		String num = "" + number;

		return num;
	}

}
