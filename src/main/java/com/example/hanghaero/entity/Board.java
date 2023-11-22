package com.example.hanghaero.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.hanghaero.dto.board.BoardRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "boards")
@NoArgsConstructor
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String bgColor;

	@Column(nullable = false)
	private String description;

	@OneToOne
	private User user;

	@OneToMany(mappedBy = "board", orphanRemoval = true)
	private List<BoardUser> boardUserList = new ArrayList<>();

	public Board(BoardRequestDto boardRequestDto) {
		this.name = boardRequestDto.getName();
		this.bgColor = boardRequestDto.getBgColor();
		this.description = boardRequestDto.getDescription();
	}

	public void update(BoardRequestDto boardRequestDto) {
		this.name = boardRequestDto.getName();
		this.bgColor = boardRequestDto.getBgColor();
		this.description = boardRequestDto.getDescription();
	}
}
