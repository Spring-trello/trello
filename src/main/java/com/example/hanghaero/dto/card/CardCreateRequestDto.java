package com.example.hanghaero.dto.card;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequestDto {
	private Long columnId;
	private String name;
	private String description;
	private String color;
	private LocalDate dueDate;
}
