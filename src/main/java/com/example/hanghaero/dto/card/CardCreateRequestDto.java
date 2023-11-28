package com.example.hanghaero.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CardCreateRequestDto {
	private Long columnId;
	private String name;
	private String description;
	private String color;
	private String dueDate;
}
