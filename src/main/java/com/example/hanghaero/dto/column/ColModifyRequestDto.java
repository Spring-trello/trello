package com.example.hanghaero.dto.column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColModifyRequestDto {
	private Long boardId;
	private String title;
}
