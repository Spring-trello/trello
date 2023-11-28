package com.example.hanghaero.dto.column;

import org.hibernate.annotations.Columns;

import com.example.hanghaero.entity.Col;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColResponseDto {
	private Long columnId;
	private String title;
	private int position;
	private Long boardId;

	public ColResponseDto(Col columns) {
		this.columnId = columns.getColumnId();
		this.title = columns.getTitle();
		this.position = columns.getPosition();
		this.boardId = columns.getBoard().getId();
	}


}
