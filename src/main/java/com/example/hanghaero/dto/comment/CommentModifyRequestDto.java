package com.example.hanghaero.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentModifyRequestDto {
	private String contents;
}
