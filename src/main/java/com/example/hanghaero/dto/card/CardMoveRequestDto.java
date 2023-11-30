package com.example.hanghaero.dto.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardMoveRequestDto {
	private Long originalColumnId;
	private int originalPosition;
}
