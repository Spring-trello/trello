package com.example.hanghaero.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.hanghaero.controller.MailController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	private static final String senderEmail = "hanghaero@hanghaero.com";
	private static int number;

	public static void createNumber(){
		number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
	}

	public MimeMessage CreateMail(String mail){
		createNumber();
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");
			String body = "";
			body += "<h3>" + "항해로 보드 초대 메일 입니다." + "</h3>";
			body += "<h1>" + number + "</h1>";
			body += "<h3>" + "감사합니다." + "</h3>";
			message.setText(body,"UTF-8", "html");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return message;
	}

	public int sendMail(String mail){
		MimeMessage message = CreateMail(mail);
		javaMailSender.send(message);

		return number;
	}
}
