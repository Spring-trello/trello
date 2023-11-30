package com.example.hanghaero.dto.card;

import java.time.format.DateTimeFormatter;

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
	private String dueDate;
	private Long columnId;
	private Long version;

	public CardResponseDto(Card card) {
		this.Id = card.getId();
		this.name = card.getName();
		this.description = card.getDescription();
		this.color = card.getColor();
		this.columnId = card.getColumn().getColumnId();
		this.dueDate = card.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.version = card.getVersion();
	}
}
