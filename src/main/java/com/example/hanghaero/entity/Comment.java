package com.example.hanghaero.entity;

import com.example.hanghaero.dto.comment.CommentModifyRequestDto;

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

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	// @ManyToOne
	// @JoinColumn(name = "parent_comment_id")
	// private Comment parentComment;

	public Comment(Card card, CommentModifyRequestDto requestDto) {
		this.comment = requestDto.getContents();
		this.card = card;
		this.user = card.getUser();
	}

	public void update(CommentModifyRequestDto requestDto) {
		this.comment = requestDto.getContents();
	}
}
