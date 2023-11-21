package com.example.hanghaero.entity;

import com.example.hanghaero.dto.BoardRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long boardId;

	@Column(name = "name")
	private String name;

	@Column(name = "bgColor")
	private String bgColor;

	@Column(name = "description")
	private String description;

	public Board(BoardRequestDto requestDto) {
		this.name = requestDto.getName();
		this.bgColor = requestDto.getBgColor();
		this.description = requestDto.getDescription();
	}

	public void update(BoardRequestDto requestDto) {
		this.name = requestDto.getName();
		this.bgColor = requestDto.getBgColor();
		this.description = requestDto.getDescription();
	}
}
