package com.example.hanghaero.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.hanghaero.dto.comment.CommentCreateRequestDto;
import com.example.hanghaero.dto.comment.CommentModifyRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
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

	@CreatedDate
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedAt;

	// @ManyToOne
	// @JoinColumn(name = "parent_comment_id")
	// private Comment parentComment;

	public Comment(Card card, User user, CommentCreateRequestDto requestDto) {
		this.comment = requestDto.getContents();
		this.card = card;
		this.user = user;
	}

	public void update(CommentModifyRequestDto requestDto) {
		this.comment = requestDto.getContents();
	}
}
