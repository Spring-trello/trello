package com.example.hanghaero.dto.comment;

import java.time.format.DateTimeFormatter;

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
	private String modifiedAt;

	public CommentResponseDto(Comment comment) {
		this.contents = comment.getComment();
		this.modifiedAt = comment.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
