package com.example.hanghaero.dto.column;

import org.hibernate.annotations.Columns;

import com.example.hanghaero.entity.Col;

import lombok.Getter;

@Getter
public class ColResponseDto {
	private String title;
	private int position;
	private Long boardId;

	public ColResponseDto(Col columns) {
		this.title = columns.getTitle();
		this.position = columns.getPosition();
		this.boardId = columns.getBoard().getBoardId();
	}
}