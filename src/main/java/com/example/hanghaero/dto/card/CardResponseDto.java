package com.example.hanghaero.dto.card;

import java.time.LocalDate;

import com.example.hanghaero.entity.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {
	private String name;
	private String description;
	private String color;
	private LocalDate dueDate;

	public CardResponseDto(Card card) {
		this.name = card.getName();
		this.description = card.getDescription();
		this.color = card.getColor();
		this.dueDate = card.getDueDate();
	}
}
