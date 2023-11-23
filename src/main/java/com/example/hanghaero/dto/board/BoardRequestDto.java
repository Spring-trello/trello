package com.example.hanghaero.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardRequestDto {

	private String name;

	private String bgColor;

	private String description;
}
