package com.example.hanghaero.dto.comment;

import java.time.format.DateTimeFormatter;

import com.example.hanghaero.entity.Comment;
import com.example.hanghaero.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
	private Long id;
	private String email;
	private String contents;
	private String modifiedAt;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.email = comment.getUser().getEmail();
		this.contents = comment.getComment();
		this.modifiedAt = comment.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
