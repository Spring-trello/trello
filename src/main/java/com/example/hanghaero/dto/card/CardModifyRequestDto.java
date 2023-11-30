package com.example.hanghaero.dto.card;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class CardModifyRequestDto {
	private String name;
	private String description;
	private String color;
	private LocalDate dueDate;
}