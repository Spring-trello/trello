package com.example.hanghaero.dto;

import com.example.hanghaero.entity.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	private String name;
	private String bgColor;
	private String description;

	public BoardResponseDto(Board board) {
		this.name = board.getName();
		this.bgColor = board.getBgColor();
		this.description = board.getDescription();
	}
}
