package com.example.hanghaero.dto.card;

import lombok.Getter;

@Getter
public class CardCreateRequestDto {
	private Long columnId;
	private String name;
	private String description;
	private String color;
	private String dueDate;
}
