package com.example.hanghaero.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequestDto {

	private String name;

	private String bgColor;

	//private String description;
}
