package com.example.hanghaero.dto.board;

import com.example.hanghaero.entity.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	private Long boardId;

	private String name;

	private String bgColor;

	private String description;

	public BoardResponseDto(Board board) {
		this.boardId = board.getId();
		this.name = board.getName();
		this.bgColor = board.getBgColor();
		this.description = board.getDescription();
	}
}
