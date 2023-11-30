package com.example.hanghaero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HanghaeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanghaeroApplication.class, args);
	}

}
