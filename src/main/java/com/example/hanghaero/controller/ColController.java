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

import com.example.hanghaero.dto.column.ColRequestDto;
import com.example.hanghaero.service.ColService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/columns")
public class ColController {
	private final ColService columnService;

	@PostMapping("")
	public ResponseEntity createColumn(@PathVariable Long boardId, @RequestBody ColRequestDto columnRequestDto) {
		return new ResponseEntity(columnService.createColumn(boardId, columnRequestDto), HttpStatus.OK);
	}

	@PutMapping("/update/{columnId}")
	public ResponseEntity updateColumn(@PathVariable Long boardId, @PathVariable Long columnId,
		@RequestBody ColRequestDto columnRequestDto) {
		return new ResponseEntity(columnService.updateColumn(boardId, columnId, columnRequestDto), HttpStatus.OK);
	}

	@DeleteMapping("/drop/{columnId}")
	public ResponseEntity deleteColumn(@PathVariable Long boardId, @PathVariable Long columnId) {
		return new ResponseEntity(columnService.deleteColumn(boardId, columnId), HttpStatus.OK);
	}

	@PutMapping("/move/{columnId}")
	public ResponseEntity updateColumn(@PathVariable Long boardId, @PathVariable Long columnId,
		@RequestParam("position") int newPosition) {
		return new ResponseEntity(columnService.moveColumn(boardId, columnId, newPosition), HttpStatus.OK);
	}
}