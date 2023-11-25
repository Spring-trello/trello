package com.example.hanghaero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hanghaero.dto.column.ColCreateRequestDto;
import com.example.hanghaero.dto.column.ColModifyRequestDto;
import com.example.hanghaero.service.ColService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/columns")
public class ColController {
	private final ColService columnService;

	@PostMapping("")
	public ResponseEntity<?> createColumn(@RequestBody ColCreateRequestDto columnRequestDto) {
		return new ResponseEntity(columnService.createColumn(columnRequestDto), HttpStatus.OK);
	}

	@PutMapping("/{columnId}")
	public ResponseEntity<?> updateColumn(@PathVariable Long columnId,
		@RequestBody ColModifyRequestDto columnRequestDto) {
		return new ResponseEntity(columnService.updateColumn(columnId, columnRequestDto), HttpStatus.OK);
	}

	@DeleteMapping("/{columnId}")
	public ResponseEntity<?> deleteColumn(@PathVariable Long columnId, @RequestParam("boardId") Long boardId) {
		return new ResponseEntity(columnService.deleteColumn(columnId, boardId), HttpStatus.OK);
	}

	// TODO: 2023-11-25 현재는 column position을 변경하면 해당 position에 있는 column과 위치를 맞바꾼다.
	//  trello에서는 드래그한 컬럼과 드롭되는 위치의 컬럼 사이에 존재하는 모든 컬럼들이 뒤로 한칸씩 밀리는 로직이 있음

	@PutMapping("/{columnId}/to/{newPosition}")
	public ResponseEntity<?> moveColumn(
		@PathVariable Long columnId,
		@PathVariable int newPosition,
		@RequestParam("boardId") Long boardId
		) {
		return new ResponseEntity(columnService.moveColumn(columnId, newPosition, boardId), HttpStatus.OK);
	}
}
