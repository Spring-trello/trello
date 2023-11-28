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
	private Long Id;
	private String name;
	private String description;
	private String color;
	private LocalDate dueDate;
	private Long columnId;

	public CardResponseDto(Card card) {
		this.Id = card.getId();
		this.name = card.getName();
		this.description = card.getDescription();
		this.color = card.getColor();
		// TODO: 2023-11-28 duedate notnull 옵션 
		//this.dueDate = card.getDueDate();
		this.columnId = card.getColumn().getColumnId();
	}
}
