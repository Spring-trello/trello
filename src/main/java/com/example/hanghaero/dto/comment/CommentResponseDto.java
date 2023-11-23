package com.example.hanghaero.dto.comment;

import com.example.hanghaero.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
	private String contents;

	public CommentResponseDto(Comment comment) {
		this.contents = comment.getComment();
	}
}
