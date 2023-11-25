package com.example.hanghaero.entity;

import com.example.hanghaero.dto.column.ColCreateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "columns")
public class Col {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long columnId;

	@Column(name = "title")
	String title;

	@Column(name = "position")
	int position = 0;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	public Col(Board board, ColCreateRequestDto requestDto, int lastPosition) {
		this.title = requestDto.getTitle();
		this.position = lastPosition + 1;
		this.board = board;
	}

	public void update(ColCreateRequestDto requestDto) {
		this.title = requestDto.getTitle();
	}

	public void updatePosition(int newPosition) {
		this.position = newPosition;
	}
}
