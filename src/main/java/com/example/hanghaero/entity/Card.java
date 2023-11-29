package com.example.hanghaero.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.example.hanghaero.dto.card.CardCreateRequestDto;
import com.example.hanghaero.dto.card.CardModifyRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card")
@NoArgsConstructor
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@ColumnDefault("'#FFFFFF'")
	private String color;

	@Column(nullable = false)
	private LocalDate dueDate;

	@Column(nullable = true)
	private int position;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "column_id")
	private Col column;

	@OneToMany(mappedBy = "card")
	private List<Comment> commentList = new ArrayList<>();

	public Card(CardCreateRequestDto requestDto, User user, Board board, Col column, int pos) {
		this.name = requestDto.getName();
		this.description = requestDto.getDescription();
		// this.color = requestDto.getColor();
		this.dueDate = requestDto.getDueDate();
		this.position = pos;
		this.user = user;
		this.board = board;
		this.column = column;
	}

	private LocalDate StringToLocalDate(String dueDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(dueDate, formatter);

		return date;
	}

	public void update(CardModifyRequestDto requestDto) {
		this.name = requestDto.getName();
		this.description = requestDto.getDescription();
		//this.color = requestDto.getColor();
		this.dueDate = StringToLocalDate(requestDto.getDueDate());
	}

	public void move(Col column, int position) {
		this.column = column;
		this.position = position;
	}
}
