package com.example.hanghaero.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.hanghaero.dto.column.ColCreateRequestDto;
import com.example.hanghaero.dto.column.ColModifyRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "columns")
public class Col {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long columnId;

	@Column(name = "title")
	String title;

	@Column(name = "position")
	int position = 0;

	@OneToMany(mappedBy = "column", orphanRemoval = true)
	List<Card> cards = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "board_id")
	Board board;

	@Version
	private Long version;

	public Col(Board board, ColCreateRequestDto requestDto, int lastPosition) {
		this.title = requestDto.getTitle();
		this.position = lastPosition + 1;
		this.board = board;
	}

	public void update(ColModifyRequestDto requestDto) {
		this.title = requestDto.getTitle();
	}

	public void updatePosition(int newPosition) {
		this.position = newPosition;
	}
}
